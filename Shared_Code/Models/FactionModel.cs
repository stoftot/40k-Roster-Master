namespace Models;

public class FactionModel
{
    public required string Id { get; init; }
    public required string Name { get; init; }
    public required string WahapediaLink { get; init; }
    public List<DetachmentModel> DetachmentModels { get; init; } = [];
}