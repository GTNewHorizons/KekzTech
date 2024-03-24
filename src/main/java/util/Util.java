package util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    /* If the number is less than 1, we round by the variable, otherwise to 2 */
    public static String toPercentageFrom(BigInteger stored, BigInteger capacity, int round) {
        BigDecimal dividend = new BigDecimal(stored);
        BigDecimal divisor = new BigDecimal(capacity);

        BigDecimal result = dividend.multiply(BigDecimal.valueOf(100)).divide(divisor, round, RoundingMode.HALF_UP);
        if (result.compareTo(BigDecimal.ONE) > 0) {
            result = result.setScale(2, RoundingMode.HALF_UP);
        }
        return result.toString();
    }

    /* Get a string like this: 4.56*10^25 */
    public static String toStandardForm(BigInteger number) {
        BigInteger abs = number.abs();
        if (abs.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0) {
            return toStandardForm(number.longValue());
        }
        String strNum = abs.toString();
        int exponent = strNum.length() - 1;
        return (number.signum() == -1 ? "-" : "") + strNum.charAt(0) + "." + strNum.substring(1, 3) + "*10^" + exponent;

    }

    /* Get a string like this: 4.56*10^5 */
    public static String toStandardForm(long number) {
        if (number == 0) {
            return "0";
        }

        int exponent = (int) Math.floor(Math.log10(Math.abs(number)));
        double mantissa = number / Math.pow(10, exponent);

        // Round the mantissa to two decimal places
        mantissa = Math.round(mantissa * 100.0) / 100.0;

        return mantissa + "*10^" + exponent;
    }
}
