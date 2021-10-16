package net.coderbot.iris.gl.uniform;

//import com.mojang.math.Vector3f;
//import com.mojang.math.Vector4f;
import java.util.function.Supplier;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
//import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL21;

public class Vector3Uniform extends Uniform {
	private final Vector3f cachedValue;
	private final Supplier<Vector3f> value;

	Vector3Uniform(int location, Supplier<Vector3f> value) {
		super(location);

		this.cachedValue = new Vector3f();
		this.value = value;
	}

	static Vector3Uniform converted(int location, Supplier<Vector3d> value) {
		Vector3f held = new Vector3f();

		return new Vector3Uniform(location, () -> {
			Vector3d updated = value.get();

			held.set((float) updated.x, (float) updated.y, (float) updated.z);

			return held;
		});
	}

	static Vector3Uniform truncated(int location, Supplier<Vector4f> value) {
		Vector3f held = new Vector3f();

		return new Vector3Uniform(location, () -> {
			Vector4f updated = value.get();

			held.set(updated.x(), updated.y(), updated.z());

			return held;
		});
	}

	@Override
	public void update() {
		Vector3f newValue = value.get();

		if (!newValue.equals(cachedValue)) {
			cachedValue.set(newValue.x(), newValue.y(), newValue.z());
			GL21.glUniform3f(location, cachedValue.x(), cachedValue.y(), cachedValue.z());
		}
	}
}
