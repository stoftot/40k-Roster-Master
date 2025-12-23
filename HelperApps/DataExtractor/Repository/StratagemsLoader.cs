using Models;

namespace Repository;

public class StratagemsLoader(string folderPath) : Loader<StratagemModel>(folderPath, new StratagemMap())
{
    private sealed class StratagemMap : ClassMap<StratagemModel>
    {
        public StratagemMap()
        {
        }
    }
}