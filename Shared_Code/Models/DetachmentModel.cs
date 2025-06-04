namespace Models;

public class DetachmentModel
{
    public required string Id { get; init; }
    public required string FactionId { get; init; }
    public required string Name { get; init; }
    public List<StratagemModel> StratagemModels { get; init; } = [];
    public List<EnhancmentModel> EnhancmentModels { get; init; } = [];
    public List<DetachmentAbilityModel> DetachmentAbilityModels { get; init; } = [];
}