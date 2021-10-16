package net.coderbot.iris.gl.uniform;

import java.util.function.Supplier;


//import net.minecraft.world.phys.Vec2;
import net.minecraft.util.math.vector.Vector2f;
import org.lwjgl.opengl.GL20;

public class Vector2Uniform extends Uniform {
	private Vector2f cachedValue;
	private final Supplier<Vector2f> value;

	Vector2Uniform(int location, Supplier<Vector2f> value) {
		super(location);

		this.cachedValue = null;
		this.value = value;

	}

	@Override
	public void update() {
		Vector2f newValue = value.get();

		if (cachedValue == null || !newValue.equals(cachedValue)) {
			cachedValue = newValue;
			GL20.glUniform2f(this.location, newValue.x, newValue.y);
		}
	}
}
