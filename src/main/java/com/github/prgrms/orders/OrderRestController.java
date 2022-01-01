package com.github.prgrms.orders;

import com.github.prgrms.configures.web.SimplePageRequest;
import com.github.prgrms.errors.BadRequestException;
import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.errors.UnauthorizedException;
import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.utils.ApiUtils;
import com.github.prgrms.utils.ApiUtils.ApiResult;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.prgrms.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {
    // TODO findAll, findById, accept, reject, shipping, complete 메소드 구현이 필요합니다.

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ApiResult<List<OrderResponse>> findAll(SimplePageRequest simplePageRequest,
                                                  @AuthenticationPrincipal JwtAuthentication authentication) {
        long offset = simplePageRequest.getOffset();
        int size = simplePageRequest.getSize();

        List<OrderResponse> ordersList = orderService.findAll(offset, size)
                .stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return success(ordersList);
    }

    @GetMapping("/{id}")
    public ApiResult<OrderResponse> findById(@PathVariable("id") Long id,
                                      @AuthenticationPrincipal JwtAuthentication authentication) {
        Orders order = orderService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find order id " + id));

        if (!Objects.equals(order.getUserSeq(), authentication.id)) {
            throw new UnauthorizedException("This order is not for user id " + id);
        }

        return success(new OrderResponse(order));
    }

    @PatchMapping("/{id}/accept")
    public ApiResult<Boolean> accept(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal JwtAuthentication authentication) {
        Orders order = orderService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find order id " + id));

        if (!Objects.equals(order.getUserSeq(), authentication.id)) {
            throw new UnauthorizedException("This order is not for user id " + id);
        }

        if (order.getState() != OrderStatus.REQUESTED) {
            return success(false);
        }
        order.setState(OrderStatus.ACCEPTED);
        orderService.updateById(order, id);

        return success(true);
    }


    @PatchMapping("/{id}/reject")
    public ApiResult<Boolean> reject(@PathVariable("id") Long id,
                                     @RequestBody(required = false) OrderRejectRequest request,
                                     @AuthenticationPrincipal JwtAuthentication authentication) {

        if(request == null){
            throw new BadRequestException("Bad request occur!");
        }

        Orders order = orderService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find order id " + id));

        if (!Objects.equals(order.getUserSeq(), authentication.id)) {
            throw new UnauthorizedException("This order is not for user id " + id);
        }

        if (order.getState() != OrderStatus.REQUESTED) {
            return success(false);
        }
        order.setState(OrderStatus.REJECTED);
        order.setRejectMsg(request.getMessage());
        order.setRejectedAt(LocalDateTime.now());
        orderService.updateById(order, id);

        return success(true);
    }


    @PatchMapping("/{id}/shipping")
    public ApiResult<Boolean> shipping(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal JwtAuthentication authentication) {

        Orders order = orderService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find order id " + id));

        if (!Objects.equals(order.getUserSeq(), authentication.id)) {
            throw new UnauthorizedException("This order is not for user id " + id);
        }

        if (order.getState() != OrderStatus.ACCEPTED) {
            return success(false);
        }
        order.setState(OrderStatus.SHIPPING);
        orderService.updateById(order, id);

        return success(true);
    }

    @PatchMapping("/{id}/complete")
    public ApiResult<Boolean> complete(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal JwtAuthentication authentication) {
        Orders order = orderService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find order id " + id));

        if (!Objects.equals(order.getUserSeq(), authentication.id)) {
            throw new UnauthorizedException("This order is not for user id " + id);
        }

        if (order.getState() != OrderStatus.SHIPPING) {
            return success(false);
        }
        order.setState(OrderStatus.COMPLETED);
        order.setCompletedAt(LocalDateTime.now());
        orderService.updateById(order, id);

        return success(true);
    }
}