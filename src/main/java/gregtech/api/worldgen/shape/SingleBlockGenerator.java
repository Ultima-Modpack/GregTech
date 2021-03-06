package gregtech.api.worldgen.shape;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.generator.IBlockGeneratorAccess;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class SingleBlockGenerator implements IShapeGenerator {

    private int blocksCount;

    public SingleBlockGenerator() {
    }

    public SingleBlockGenerator(int blocksCount) {
        this.blocksCount = blocksCount;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        if(object.has("blocks_count")) {
            this.blocksCount = object.get("blocks_count").getAsInt();
        } else this.blocksCount = 1;
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess) {
        MutableBlockPos relativePos = new MutableBlockPos();
        EnumFacing prevDirection = null;
        for(int i = 0; i < blocksCount; i++) {
            EnumFacing[] allowedFacings = ArrayUtils.removeElement(EnumFacing.VALUES, prevDirection);
            relativePos.offset(allowedFacings[gridRandom.nextInt(allowedFacings.length)]);
            relativeBlockAccess.generateBlock(relativePos.getX(), relativePos.getY(), relativePos.getZ());
        }
    }
}
