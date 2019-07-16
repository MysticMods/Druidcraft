package mystic.druidcraft.entity.render;

import mystic.druidcraft.Druidcraft;
import mystic.druidcraft.entity.DreadFishEntity;
import mystic.druidcraft.entity.model.DreadFishModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DreadFishRenderer extends MobRenderer<DreadFishEntity, DreadFishModel<DreadFishEntity>> {
    private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation(Druidcraft.MODID, "textures/entity/dreadfish/dreadfish.png");

    public DreadFishRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DreadFishModel<>(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(DreadFishEntity entity) {
        return SQUID_TEXTURES;
    }

}