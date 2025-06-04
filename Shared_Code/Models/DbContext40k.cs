using Microsoft.EntityFrameworkCore;

namespace Models;

public class DbContext40k : DbContext {
    public virtual DbSet<FactionModel> Factions { get; set; }
    public virtual DbSet<DetachmentModel> Detachments { get; set; }
    public virtual DbSet<DetachmentAbilityModel> DetachmentAbilities { get; set; }
    public virtual DbSet<EnhancmentModel> Enhancments { get; set; }
    public virtual DbSet<StratagemModel> Stratagems { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        #region Tabels
        
        modelBuilder.Entity<FactionModel>(e =>
            {
                e.ToTable("Factions");
                e.HasKey(x => x.Id);
                e.Property(x => x.Id).ValueGeneratedNever();
            }
        );

        modelBuilder.Entity<DetachmentModel>(e =>
        {
            e.ToTable("Detachments");
            e.HasKey(x => x.Id);
            e.Property(x => x.Id).ValueGeneratedNever();
        });

        modelBuilder.Entity<DetachmentAbilityModel>(e =>
        {
            e.ToTable("DetachmentAbilities");
            e.HasKey(x => x.Id);
            e.Property(x => x.Id).ValueGeneratedNever();
        });

        modelBuilder.Entity<EnhancmentModel>(e =>
        {
            e.ToTable("Enhancments");
            e.HasKey(x => x.Id);
            e.Property(x => x.Id).ValueGeneratedNever();
        });

        modelBuilder.Entity<StratagemModel>(e =>
        {
            e.ToTable("Stratagems");
            e.HasKey(x => x.Id);
            e.Property(x => x.Id).ValueGeneratedNever();
        });
        
        #endregion

        #region Relations

        modelBuilder.Entity<DetachmentModel>()
            .HasOne<FactionModel>()
            .WithMany(f => f.DetachmentModels)
            .HasForeignKey(d => d.FactionId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<StratagemModel>()
            .HasOne<DetachmentModel>()
            .WithMany(d => d.StratagemModels)
            .HasForeignKey(s => s.DetachmentId)
            .OnDelete(DeleteBehavior.Cascade);
        
        modelBuilder.Entity<EnhancmentModel>()
            .HasOne<DetachmentModel>()
            .WithMany(d => d.EnhancmentModels)
            .HasForeignKey(e => e.DetachmentId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<DetachmentAbilityModel>()
            .HasOne<DetachmentModel>()
            .WithMany(d => d.DetachmentAbilityModels)
            .HasForeignKey(f => f.DetachmentId)
            .OnDelete(DeleteBehavior.Cascade);
        
        #endregion
    }
}