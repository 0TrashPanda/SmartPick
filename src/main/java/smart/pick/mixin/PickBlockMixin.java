package smart.pick.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import org.anti_ad.mc.ipn.api.access.IPN;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import smart.pick.SmartPick;

@Mixin(PlayerInventory.class)
public class PickBlockMixin {
    @Inject(method = "getSwappableHotbarSlot()I", at = @At("HEAD"), cancellable = true)
    public void SkipLockedSlots(CallbackInfoReturnable<Integer> ci) {
        if (!SmartPick.configSkipLocked.getBooleanValue()) {
            return;
        }
        @SuppressWarnings("unchecked")
        List<Integer> lockedSlots = IPN.getInstance().getLockedSlots();
        List<Integer> hotbarSlots = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        // if all or none of the hotbar slots are locked
        if (lockedSlots.containsAll(hotbarSlots)) {
            System.out.println("all hotbar slots are locked");
            return;
        }

        int selectedSlot = ((PlayerInventory) (Object) this).selectedSlot;
        DefaultedList<ItemStack> main = ((PlayerInventory) (Object) this).main;
        for (int i = 0; i < 9; i++) {
            int j = (selectedSlot + i) % 9;
            if (main.get(j).isEmpty() && !lockedSlots.contains(j)) {
                ci.setReturnValue(j);
                return;
            }
        }

        for (int ix = 0; ix < 9; ix++) {
            int j = (selectedSlot + ix) % 9;
            if (!main.get(j).hasEnchantments() && !lockedSlots.contains(j)) {
                ci.setReturnValue(j);
                return;
            }
        }
    }
}
