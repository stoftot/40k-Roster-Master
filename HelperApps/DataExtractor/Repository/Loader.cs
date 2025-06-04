using System.Globalization;
using System.Text;
using System.Text.RegularExpressions;
using CsvHelper;
using CsvHelper.Configuration;

namespace Repository;

public abstract class Loader<T>
{
    protected abstract string FileName { get; }

    protected List<T> Elements { get; private set; }

    private string FolderPath { get; }
    private string FilePath => $"{FolderPath}/{FileName}";
    private string TempFilePath => $"{FolderPath}/Temp-{FileName}";

    private CsvReader CsvReader { get; }

    protected Loader(string folderPath, ClassMap<T> classMap)
    {
        FolderPath = folderPath;

        RemoveEmptyColoumns();

        var supposedNumberOfRecords = CountLines();
        
        using var reader = new StreamReader(FilePath);

        var config = new CsvConfiguration(CultureInfo.InvariantCulture)
        {
            Delimiter = "|",
            PrepareHeaderForMatch = args => args.Header.ToLower(),
            TrimOptions = TrimOptions.Trim,
            BadDataFound = null,
        };

        CsvReader = new CsvReader(reader, config);

        Elements = Load(classMap);

        if (Elements.Count != supposedNumberOfRecords)
        {
            throw new Exception($"""
                                 Error trying to load elements: {FilePath}
                                 There was supposed to be "{supposedNumberOfRecords}" 
                                 but there was "{Elements.Count}"
                                 """);
        }
    }

    private List<T> Load(ClassMap<T> classMap)
    {
        CsvReader.Context.RegisterClassMap(classMap);
        return CsvReader.GetRecords<T>().ToList();
    }

    private void RemoveEmptyColoumns()
    {
        using (var reader = new StreamReader(FilePath))
        using (var writer = new StreamWriter(TempFilePath))
        {
            // Read the header line
            var headerLine = reader.ReadLine();
            if (headerLine == null)
                throw new InvalidOperationException("CSV file is empty.");

            var headers = headerLine.Split('|');
            var nonEmptyHeaderIndices = headers
                .Select((header, index) => new { header, index })
                .Where(h => !string.IsNullOrWhiteSpace(h.header))
                .Select(h => h.index)
                .ToList();

            // Write the cleaned header
            var cleanedHeaders = nonEmptyHeaderIndices.Select(index => headers[index]);
            writer.WriteLine(string.Join("|", cleanedHeaders));

            // Process and write the remaining lines
            string line;
            while ((line = reader.ReadLine()) != null)
            {
                var fields = line.Split('|');
                var cleanedFields = nonEmptyHeaderIndices
                    .Where(index => index < fields.Length)
                    .Select(index => fields[index]);
                writer.WriteLine(string.Join("|", cleanedFields));
            }
        }

        File.Delete(FilePath);
        File.Move(TempFilePath, FilePath);
    }

    public int CountLines()
    {
        var totalLines = File.ReadLines(FilePath).Count();
        return totalLines - 1;
    }
}