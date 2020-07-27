package debug;

import Exceptions.InputOutOfBoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * In a Store, there are some kinds of items to sell. Each item has a price.
 * 
 * However, there are some special offers, and a special offer consists of one
 * or more different kinds of items with a sale price.
 * 
 * You are given the each item's price, a set of special offers, and the number
 * we need to buy for each item. The job is to output the lowest price you have
 * to pay for exactly certain items as given, where you could make optimal use
 * of the special offers.
 * 
 * Each special offer is represented in the form of an array, the last number
 * represents the price you need to pay for this special offer, other numbers
 * represents how many specific items you could get if you buy this offer.
 * 
 * You could use any of special offers as many times as you want.
 * 
 * Example 1:
 * 
 * Input: [2,5], [[3,0,5],[1,2,10]], [3,2] Output: 14
 * 
 * Explanation:
 * 
 * There are two kinds of items, A and B. Their prices are $2 and $5
 * respectively.
 * 
 * In special offer 1, you can pay $5 for 3A and 0B
 * 
 * In special offer 2, you can pay $10 for 1A and 2B.
 * 
 * You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer
 * #2), and $4 for 2A.
 * 
 * Example 2:
 * 
 * Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1] Output: 11
 * 
 * Explanation:
 * 
 * The price of A is $2, and $3 for B, $4 for C.
 * 
 * You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C.
 * 
 * You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer
 * #1), and $3 for 1B, $4 for 1C.
 * 
 * You cannot add more items, though only $9 for 2A ,2B and 1C.
 * 
 * 
 * Note:
 * 
 * 1. There are at most 6 kinds of items, 100 special offers.
 * 
 * 2. For each item, you need to buy at most 6 of them.
 * 
 * 3. You are not allowed to buy more items than you want, even if that would
 * lower the overall price.
 * 
 * ---------------------------------------------------------------------------------------
 * Please debug and fix potential bugs in the following code and make it execute correctly,
 * robust, and completely in accordance with above requirements.
 * 
 * DON'T change the initial logic of the code.
 * ---------------------------------------------------------------------------------------
 * 
 */
public class LowestPrice {

	public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		//检测输入的合法性
		try{
			if(price.size()>6 || special.size()>100 || needs.size()>6){
				throw new InputOutOfBoundException("输入物品种类数/special offer/需求种类数超出限制");
			}
			for(Integer tempNumber : needs){
				if(tempNumber>6){
					throw new InputOutOfBoundException("某件物品的需求大于6件");
				}
			}
			//检测三个输入参数的数量关系
			int itemNumber = price.size();
			if(needs.size()!=itemNumber){
				throw new InputOutOfBoundException("提供物品种类和需求物品种类不符");
			}
			for(List<Integer> tempOffer : special){
				if(tempOffer.size() != itemNumber+1){
					throw new InputOutOfBoundException("special offer物品种类和提供物品种类不符");
				}
			}
		}
		catch (InputOutOfBoundException e){
			e.printStackTrace();
			return -1;
		}

		return shopping(price, special, needs);
	}

	public int shopping(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		int j = 0, res = dot(needs, price);
		for (List<Integer> s : special) {
			List<Integer> clone = new ArrayList<>(needs);
			for (j = 0; j < needs.size(); j++) {
				int diff = clone.get(j) - s.get(j);
				//如果一个special offer提供的某项物品的个数超出需要，则考察下一个special offer
				if (diff < 0)
					break;
				clone.set(j, diff);
			}
			//某项special offer提供每个物品的个数不超过所需
			if (j == needs.size()){
				//如果使用此offer价格更低则采用，否则跳过
				res = Math.min(res, s.get(j) + shopping(price, special, clone));
			}
		}
		return res;
	}

	public int dot(List<Integer> a, List<Integer> b) {
		int sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i) * b.get(i);
		}
		return sum;
	}

}
