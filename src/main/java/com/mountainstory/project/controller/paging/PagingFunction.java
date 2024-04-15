package com.mountainstory.project.controller.paging;

import com.mountainstory.project.dto.review.ReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class PagingFunction {

    public void getPagingDataAndModel(Page<ReviewInfo> reviewList, Model model ) {
        int nowPage = reviewList.getPageable().getPageNumber() + 1;
        //reviewList 에서 페이지 객체를 호출하고, 페이지 객체에서 현재 페이지 번호를 가져옴. 인덱스 기준이라 0을 출력하기 때문에 +1 을 해준다

        int startPage =  Math.max(nowPage - 4, 1);
        //Math.max 의 기능: 두 수를 비교해서 더 큰 값을 반환함. => 1 보다 작은 수가 들어가면 안되기 때문에 만약 nowPage - 1 값이 1 보다 작을 경우에 1이 그냥 반환되도록 함
        // nowPage - 4 : 현재 페이지에서 4를 빼는 수식인데, 이렇게 하는 이유는 페이징 번호가 기하급수 적으로 늘어나는것을 방지하기 위함임
        // ex) 6 페이지에 접근할 시 => [2,3,4,5,6] 이렇게 표시됌. 즉, 페이징 표시 번호가 마구 늘어나는것을 제한하기 위함임
        //페이징이 많아져도 늘 현재 페이지에서 -4 한 만큼만 보여짐

        int endPage = Math.min(nowPage+9, reviewList.getTotalPages());
        // Math.min : 두개 중에 더 작은 값을 선정함.
        //nowPage+9 : 현재 페이지에서 +9 한 페이징 번호만 출력함. 이유는 만약 페이징 번호가 100까지 있다면, 100까지 다 출력하는것이 불가능하기 때문!
        //그래서 최대 페이지를 현재 페이지에 +9 한 만큼만 보여주는것!
        // reviewList.getTotalPages() : 전체 페이지가 +9 보다 작을수도 있음. (9 보다 작음) 그럴때는 그냥 모든 페이지를 출력함

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    }
}
