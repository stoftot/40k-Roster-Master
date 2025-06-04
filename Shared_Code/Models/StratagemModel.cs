using Enums;

namespace Models;

public class StratagemModel
{
    public required string Id { get; init; }
    public required string DetachmentId { get; init; }
    public required string Name { get; init; }
    public required StratagemType Type { get; init; }
    public required int CpCost  { get; init; }
    public required Turn TurnToBeUsed { get; init; }
    public List<Phase> PhasesToBeUsed { get; init; } = [];
    public required string Description { get; init; }
}