package faceweb.demo.Controller;
implementation 'org.python:jython-slim:2.7.4rc1'
import faceweb.demo.DTO.PredictDTO;
import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.StyleEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

// Jython
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

@Controller
public class StyleController {

    @PostMapping("/user/face/upload")
    public String imageUploadProcess(@ModelAttribute PredictDTO predictDTO,
                                     RedirectAttributes redirectAttributes){

        /*
        * 여기에 flask 서버와 통신하여 이미지를 받아오는 부분 작성
        
        * */

        MultipartFile file = predictDTO.getImage();
        if(file.isEmpty()){
            redirectAttributes.addAttribute("error", "빈 파일입니다.");
            return "redirect:/user/error";
        }

        try {
            // 서버에 파일 저장
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(bytes);
            stream.close();
            redirectAttributes.addAttribute("fileName", fileName);
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "파일 업로드 실패: " + e.getMessage());
        }

        private static PythonInterpreter interpreter;
        public static void mainStirng] args){

            image = bytes;
            interpreter = new PythonInterpreter();
            interpreter.execfile("src/main/java/service/predict.py");
            interpreter.exec("result = ");

            PyObject result = interpreter.get("result");
            System.out.println(result);
        }

        return "redirect:/user/face/recommend";
    }

    @GetMapping("/user/face/recommend")
    public String recommendPage(Model model, @RequestParam("fileName")String fileName, @ModelAttribute PredictDTO predictDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StyleEntity style = new StyleEntity();
        style.setDescription("description1");
        style.setFilePath(fileName);
        style.setPerson_see_up_dos(predictDTO.getPerson_see_up_dos());
        style.setPerson_hair_length(predictDTO.getPerson_hair_length());
        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
            style.setUser(currentUser);
        }
        else
            style.setUser(null);

        model.addAttribute("style", style);

        return "/user/rateRecommend";
    }
}
