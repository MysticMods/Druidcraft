package mystic.druidcraft.entity.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DreadFishModel<T extends Entity> extends EntityModel<T> {
    private final RendererModel spine1;
    private final RendererModel head;
    private final RendererModel head2;
    private final RendererModel spine2;
    private final RendererModel tail;
    private final RendererModel sidefinR;
    private final RendererModel sidefinL;

    public DreadFishModel() {
        textureWidth = 32;
        textureHeight = 32;

        spine1 = new RendererModel(this);
        spine1.setRotationPoint(0.0F, 14.0F, -2.0F);
        spine1.cubeList.add(new ModelBox(spine1, 0, 0, -1.5F, -2.5F, -3.0F, 3, 5, 6, 0.0F, false));
        spine1.cubeList.add(new ModelBox(spine1, 10, 20, 0.0F, -4.5F, 0.0F, 0, 2, 3, 0.0F, false));

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, 0.0F, -3.0F);
        spine1.addChild(head);
        head.cubeList.add(new ModelBox(head, 8, 11, -1.0F, -2.0F, -3.0F, 2, 3, 3, 0.0F, false));

        head2 = new RendererModel(this);
        head2.setRotationPoint(0.0F, 1.0F, 0.0F);
        head.addChild(head2);
        head2.cubeList.add(new ModelBox(head2, 0, 20, -1.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F, false));

        spine2 = new RendererModel(this);
        spine2.setRotationPoint(0.0F, 0.0F, 3.0F);
        spine1.addChild(spine2);
        spine2.cubeList.add(new ModelBox(spine2, 18, 0, -1.0F, -2.5F, 0.0F, 2, 4, 4, 0.0F, false));

        tail = new RendererModel(this);
        tail.setRotationPoint(0.0F, 0.0F, 4.0F);
        spine2.addChild(tail);
        tail.cubeList.add(new ModelBox(tail, 0, 11, 0.0F, -2.5F, 0.0F, 0, 5, 4, 0.0F, false));

        sidefinR = new RendererModel(this);
        sidefinR.setRotationPoint(-1.5F, 1.5F, -2.0F);
        setRotationAngle(sidefinR, 0.0F, 0.0F, -0.7854F);
        spine1.addChild(sidefinR);
        sidefinR.cubeList.add(new ModelBox(sidefinR, 16, 20, -2.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F, false));

        sidefinL = new RendererModel(this);
        sidefinL.setRotationPoint(1.5F, 1.5F, -2.0F);
        setRotationAngle(sidefinL, 0.0F, 0.0F, 0.7854F);
        spine1.addChild(sidefinL);
        sidefinL.cubeList.add(new ModelBox(sidefinL, 0, 25, 0.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        spine1.render(f5);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
