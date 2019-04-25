package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;
import me.blackness.black.Requirement;

/*
       .                                                    .
    .$"                                    $o.      $o.  _o"
   .o$$o.    .o$o.    .o$o.    .o$o.   .o$$$$$  .o$$$$$ $$P  `4$$$$P'   .o$o.
  .$$| $$$  $$' $$$  $$' $$$  $$' $$$ $$$| $$$ $$$| $$$ ($o  $$$: $$$  $$' $$$
  """  """ """  """ """  """ """  """ """  """ """  """  "   """  """ """  """
.oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo.
  ooo_ ooo ooo. ... ooo. ... ooo.  .. `4ooo.  .`4ooo.   ooo. ooo. ooo ooo.  ..
  $$$"$$$$ $$$| ... $$$| ... $$$$$$ ..    "$$o     "$$o $$$| $$$| $$$ $$$|   .
  $$$| $$$ $$$|     $$$|     $$$|     $$$: $$$ $$$: $$$ $$$| $$$| $$$ $$$|
  $$$| $$$ $$$| $o. $$$| $o. $$$| $o. $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $o.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $$$
  $$$| $$$  $$. $$$  $$. $$$  $$. $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$  $$. $$$
  $$$: $P'  `4$$$Ü'__`4$$$Ü'  `4$$$Ü' $$$$$P'  $$$$$P'  $$$| $$$: $P' __`4$$$Ü'
 _ _______/∖______/  ∖______/∖______________/|________ "$P' _______/  ∖_____ _
                                                        i"  personinblack
                                                        |
 */

/**
 * a requirement which requires an element to be transferred.
 *
 * @author personinblack
 * @see Requirement
 * @since 4.0.0-alpha
 */
public final class TransferredElementReq implements Requirement {
    private final Element element;

    /**
     * ctor.
     *
     * @param element the element that has to be transferred
     */
    public TransferredElementReq(final Element element) {
        this.element = Objects.requireNonNull(element);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            final InventoryClickEvent clickEvent = (InventoryClickEvent) event;
            int assumption = -1;
            Inventory target = clickEvent.getInventory();
            if (clickEvent.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                if (event.getView().getTopInventory().equals(clickEvent.getClickedInventory())) {
                    target = event.getView().getBottomInventory();
                } else {
                    target = event.getView().getTopInventory();
                }

                assumption = target.firstEmpty();

                final ItemStack clickedItem = clickEvent.getCurrentItem();
                for (int i = 0; i < target.getSize(); i++) {
                    final ItemStack item = target.getItem(i);
                    if (item != null && item.isSimilar(clickedItem) &&
                            item.getMaxStackSize() - item.getAmount() >= clickedItem.getAmount()) {

                        assumption = i;
                    }
                }
            } else if (clickEvent.getAction().name().contains("PLACE")) {
                assumption = clickEvent.getSlot();
            }
            final ItemStack found = target.getItem(assumption) == null
                ? new ItemStack(Material.AIR)
                : target.getItem(assumption);
            return assumption >= 0
                ? element.is(found)
                : false;
        }
        return false;
    }
}
