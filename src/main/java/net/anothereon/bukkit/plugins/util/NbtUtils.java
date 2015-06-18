package net.anothereon.bukkit.plugins.util;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.io.NbtBinarySerializer;
import com.google.common.io.Closeables;
import org.bukkit.World;

import java.io.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Christian on 13/06/2015.
 */
public class NbtUtils {

    public static NbtCompound loadPlayerData(World world, UUID playerUuid) throws IOException {
        File worldDirectory = world.getWorldFolder();

        if (!worldDirectory.exists())
            throw new IOException(
                    String.format("World directory %s does not exist.", worldDirectory));

        File dataFile = new File(new File(worldDirectory, "playerdata"), playerUuid.toString() + ".dat");

        if (!dataFile.exists())
            throw new IOException(
                    String.format("Data file %s does not exist in world %s", dataFile.getName(), worldDirectory.getName()));

        return loadData(dataFile.getAbsolutePath());
    }

    public static void savePlayerData(World world, UUID playerUuid, NbtCompound data) throws IOException {
        File worldDirectory = world.getWorldFolder();

        if (!worldDirectory.exists())
            throw new IOException(
                    String.format("World directory %s does not exist.", worldDirectory));

        File dataFile = new File(new File(worldDirectory, "playerdata"), playerUuid.toString() + ".dat");

        saveData(dataFile.getAbsolutePath(), data);

    }

    public static NbtCompound loadLevelData(World world) throws IOException {
        File levelData = new File(world.getWorldFolder(), "level.dat");

        if(!levelData.exists())
            throw new IOException(
                    String.format("The world %s in directory %s doesn not exist.", world.getName(), world.getWorldFolder().getName())
            );

        return loadData(levelData.getAbsolutePath());

    }

    public static void saveLevelData(World world, NbtCompound data) throws IOException {
        File worldDirectory = world.getWorldFolder();

        if (!worldDirectory.exists())
            throw new IOException(
                    String.format("World directory %s does not exist.", worldDirectory));

        File dataFile = new File(worldDirectory, "level.dat");

        saveData(dataFile.getAbsolutePath(), data);

    }

    private static NbtCompound loadData(String path) throws IOException {
        NbtCompound data = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {

            fis = new FileInputStream(path);
            dis = new DataInputStream(new GZIPInputStream(fis));

            data = NbtBinarySerializer.DEFAULT.deserializeCompound(dis);

            if (data == null)
                throw new IOException("data is null!");

        } finally {
            if (dis != null)
                Closeables.closeQuietly(dis);
            if (fis != null)
                Closeables.closeQuietly(fis);
        }
        return data;
    }

    private static void saveData(String path, NbtCompound data) throws IOException {
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {

            fos = new FileOutputStream(path);
            dos = new DataOutputStream(new GZIPOutputStream(fos));

            NbtBinarySerializer.DEFAULT.serialize(data, dos);
        } finally {
            if (dos != null)
                Closeables.closeQuietly(dos);
            if (fos != null)
                Closeables.closeQuietly(fos);
        }
    }
}
