package util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import common.items.ErrorItem;

public class Util {

    public static ItemStack getStackofAmountFromOreDict(String oredictName, final int amount) {
        final ArrayList<ItemStack> list = OreDictionary.getOres(oredictName);
        if (!list.isEmpty()) {
            final ItemStack ret = list.get(0).copy();
            ret.stackSize = amount;
            return ret;
        }
        System.err.println("Failed to find " + oredictName + " in OreDict");
        return new ItemStack(ErrorItem.getInstance(), amount);
    }

    public static ItemStack[] toItemStackArray(List<ItemStack> stacksList) {
        if (stacksList.size() == 0) {
            return null;
        }

        ItemStack[] ret = new ItemStack[stacksList.size()];
        Iterator<ItemStack> iterator = stacksList.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    public static FluidStack[] toFluidStackArray(List<FluidStack> stacksList) {
        if (stacksList.size() == 0) {
            return null;
        }

        FluidStack[] ret = new FluidStack[stacksList.size()];
        Iterator<FluidStack> iterator = stacksList.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    /* If the number is less than 1, we round by the 6, otherwise to 2 */
    public static String toPercentageFrom(BigInteger value, BigInteger maxValue) {
        BigDecimal result = new BigDecimal(value).setScale(6, RoundingMode.HALF_UP)
                .divide(new BigDecimal(maxValue), RoundingMode.HALF_UP);
        if (result.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            return new DecimalFormat("0.000000%").format(result);
        } else {
            return new DecimalFormat("0.00%").format(result);
        }
    }

    /* Get a string like this: 4.56*10^25 */
    public static String toStandardForm(BigInteger number) {
        if (BigInteger.ZERO.equals(number)) {
            return "0";
        }

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        dfs.setExponentSeparator("x10^");
        DecimalFormat format = new DecimalFormat("0.00E0", dfs);

        return format.format(number);
    }

}
