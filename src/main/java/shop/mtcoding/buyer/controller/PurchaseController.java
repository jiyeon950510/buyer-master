package shop.mtcoding.buyer.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.buyer.model.Product;
import shop.mtcoding.buyer.model.ProductRepository;
import shop.mtcoding.buyer.model.PurchaseRepository;
import shop.mtcoding.buyer.model.User;

@Controller
public class PurchaseController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    /*
     * 목적 : 세션이 있는지 체크, 구매 히스토리 남기기, 재고 수량 변경,
     */
    @PostMapping("/pruchase/insert")
    public String insert(int productId, int count) {
        User principal = (User) session.getAttribute("principal");
        // 1. 세션이 있는지 체크
        if (principal == null) {
            return "redirect:/notfound";
        }
        // 2. 구매 이력 남기기
        int result = purchaseRepository.insert(principal.getId(), productId, count);
        if (result != 1) {
            return "redirect:/notfound";
        }
        // 3. 상품 존재 확인 (검증 : 클라이언트의 데이터는 신뢰할수 없음)
        Product product = productRepository.findById(productId);
        if (product == null) {
            return "redirect:/notfound";
        }
        // 4. 재고수량 변경 (1~4 : 비지니스 로직!)
        int result2 = productRepository.updateById(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQty() - count);

        if (result2 != 1) {
            return "redirect:/notfound";
        }

        return "redirect:/";
    }
}
