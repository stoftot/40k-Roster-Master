// See https://aka.ms/new-console-template for more information

using Repository;

// var test = new Test();
var baseDirecoty = Directory.GetParent(Directory.GetCurrentDirectory())!.Parent!.Parent!.Parent!.Parent!.Parent!
    .FullName;
var wahapediaDataPath = $"{baseDirecoty}/Whapedia data";

var enhancmentLoader = new EnhancementsLoader(wahapediaDataPath);

// Console.WriteLine(baseDirecoty);
// Console.WriteLine(wahapediaDataPath);
//
// string[] files = Directory.GetFiles(wahapediaDataPath);
// foreach (string file in files)
// {
//     Console.WriteLine(Path.GetFileName(file));
// }
//
// Console.WriteLine("\nDirectories:");
// string[] directories = Directory.GetDirectories(wahapediaDataPath);
// foreach (string dir in directories)
// {
//     Console.WriteLine(Path.GetFileName(dir));
// }

Console.WriteLine("Hello, World!");