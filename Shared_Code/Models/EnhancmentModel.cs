namespace Models;

public class EnhancmentModel
{
    public required string Id { get; init; }
    public required string DetachmentId { get; init; }
    public required string Name { get; init; }
    public required string Description { get; init; }
    public required int PointsCost { get; init; }
}