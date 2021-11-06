/**
 * 1011. Capacity To Ship Packages Within D Days:
 *
 * 解决方案：二分搜索左侧边界。将载重量从低到高当作有序数组，来查找符合条件的最低的值（最左侧的边界）
 *
 * A conveyor belt has packages that must be shipped from one port to another within days days.
 *
 * The ith package on the conveyor belt has a weight of weights[i].
 * Each day, we load the ship with packages on the conveyor belt (in the order given by weights).
 * We may not load more weight than the maximum weight capacity of the ship.
 *
 * Return the least weight capacity of the ship that will
 * result in all the packages on the conveyor belt being shipped within days days.
 *
 * 在 D 天内送达包裹的能力：
 * 传送带上的包裹必须在 D 天内从一个港口运送到另一个港口。
 * 传送带上的第 i 个包裹的重量为 weights[i]。
 * 每一天，我们都会按给出重量的顺序往传送带上装载包裹。
 * 我们装载的重量不会超过船的最大运载重量。
 * 返回能在 D 天内将传送带上的所有包裹送达的船的最低运载能力。
 *
 * 链接：https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days
 */
class Solution {

    public int shipWithinDays(int[] weights, int days) {

        // left 表示最小载重，即 weights 数组中最大的那个重量。
        // 因为如果小于最大的重量，最大的重量就因为超重而不能运了
        int left = getMin(weights);

        // 最高的载重，就是一次性运完所有的内容，也就是 weights 数组中所有元素的总和
        int max = getMax(weights);
        // 这里使用 [left, right) 区间，所以需要 +1
        int right = max + 1;

        // 在 [left, right) 中循环
        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean canFinish = canFinish(weights, mid, days);
            if (canFinish) {
                // 继续向左寻找，看看有没有更小的载重：[left, mid)
                right = mid;
            } else {
                // 如果不能按时完成，就提高载重，也就是往右移动:[mid + 1, right)
                left = mid + 1;
            }
        }
        // return right; 也是一样的
        return left;
    }

    private int getMin(int[] weights) {
        // 获取 weights 数组中最大的数
        int maxWeight = 0;
        for (int w : weights) {
            if (maxWeight < w) {
                maxWeight = w;
            }
        }
        return maxWeight;
    }

    private int getMax(int[] weights) {
        // 获取 weights 数组中，所有数的总和
        int sum = 0;
        for (int weight : weights) {
            sum += weight;
        }
        return sum;
    }

    private boolean canFinish(int[] weights, int capEveryDay, int withinDays) {
        // index 是 weights 数组中的 index
        int index = 0;

        // 在规定的天数内遍历
        for (int day = 1; day <= withinDays; day++) {
            // leftCap 表示每天剩余的载重量
            int leftCap = capEveryDay;
            while ((leftCap -= weights[index]) >= 0) {
                // 只要剩余的载重量 - 该 index 位置货物的重量 >= 0，
                // 说明可以运送 index 位置的货物
                // 将 index + 1，为下次运送做准备
                index++;

                if (index == weights.length) {
                    // 如果运送完 weights 数组中的所有货物后，可以提前返回 true
                    return true;
                }
            }
        }

        // 如果在规定天数内没有完成，就返回 false
        return false;
    }

}