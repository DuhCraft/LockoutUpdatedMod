package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Hashtable;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BredAnimalsTrigger.class})
public class MixinBredAnimalsTrigger {
    public MixinBredAnimalsTrigger() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/animal/Animal;Lnet/minecraft/world/entity/animal/Animal;Lnet/minecraft/world/entity/AgeableMob;)V"}
    )
    protected void onTrigger(ServerPlayer playerEntity, Animal p_147280_, Animal p_147281_, AgeableMob p_147282_, CallbackInfo info) {
        Hashtable<String, HashSet<String>> unique_breeds = null;
        String animalName = p_147280_.m_7755_().getString().toLowerCase();
        if (LockoutBingoSetup.checkBreed4 || LockoutBingoSetup.checkBreed6 || LockoutBingoSetup.checkBreed8) {
            try {
                Path filename = Paths.get(playerEntity.m_20194_().m_6237_().toString(), "lockoutBreedData.txt");

                try {
                    FileInputStream file_in = new FileInputStream(filename.toString());
                    ObjectInputStream in = new ObjectInputStream(file_in);
                    unique_breeds = (Hashtable)in.readObject();
                    in.close();
                    file_in.close();
                } catch (FileNotFoundException var11) {
                    unique_breeds = new Hashtable();
                }

                if (unique_breeds.containsKey(playerEntity.m_5446_().getString())) {
                    ((HashSet)unique_breeds.get(playerEntity.m_5446_().getString())).add(animalName);
                } else {
                    unique_breeds.put(playerEntity.m_5446_().getString(), new HashSet());
                    ((HashSet)unique_breeds.get(playerEntity.m_5446_().getString())).add(animalName);
                }

                if (LockoutBingoSetup.checkBreed4 && ((HashSet)unique_breeds.get(playerEntity.m_5446_().getString())).size() >= 4 && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.breed4Index, playerEntity.m_5446_().getString())) {
                    LockoutBingoSetup.checkBreed4 = false;
                }

                if (LockoutBingoSetup.checkBreed6 && ((HashSet)unique_breeds.get(playerEntity.m_5446_().getString())).size() >= 6 && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.breed6Index, playerEntity.m_5446_().getString())) {
                    LockoutBingoSetup.checkBreed6 = false;
                }

                if (LockoutBingoSetup.checkBreed8 && ((HashSet)unique_breeds.get(playerEntity.m_5446_().getString())).size() >= 8 && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.breed8Index, playerEntity.m_5446_().getString())) {
                    LockoutBingoSetup.checkBreed8 = false;
                }

                FileOutputStream file_out = new FileOutputStream(filename.toString());
                ObjectOutputStream out = new ObjectOutputStream(file_out);
                out.writeObject(unique_breeds);
                out.close();
                file_out.close();
            } catch (ClassNotFoundException | IOException var12) {
                LockoutBingoMod.LOGGER.info("Can't save Breed Data");
            }
        }

    }
}
