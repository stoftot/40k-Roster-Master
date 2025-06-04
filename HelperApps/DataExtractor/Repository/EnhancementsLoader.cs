using CsvHelper.Configuration;
using Models;

namespace Repository;

public class EnhancementsLoader(string folderPath) : Loader<EnhancmentModel>(folderPath, new EnhancmentMap())
{
    protected override string FileName => "Enhancements.csv";

    public List<EnhancmentModel> EnhancmentModels => Elements;

    private sealed class EnhancmentMap : ClassMap<EnhancmentModel>
    {
        public EnhancmentMap()
        {
            Map(x => x.Id).Name("id");
            Map(x => x.Name).Name("name");
            Map(x => x.Description).Name("description");
            Map(x => x.PointsCost).Name("cost");
            Map(x => x.DetachmentId).Name("detachment");
        }
    }
}