package faceweb.demo.Interceptor;

import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.UserEntity;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetail){
            UserEntity currentUser = currentUserDetail.getUserEntity();
            request.setAttribute("username", currentUser.getUsername());
            request.setAttribute("userRole", currentUser.getRole());
        }

        return true;
    }
}
