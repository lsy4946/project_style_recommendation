package faceweb.demo.Service;

import faceweb.demo.DTO.RateStyleDTO;
import faceweb.demo.Entity.StyleEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.StyleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class StyleService {

    private final String pythonPath;
    private final StyleRepository styleRepository;
//    private final Process recommendingProcess;
//    private final BufferedWriter writer;
//    private final BufferedReader reader;
    private final int TIMEOUT_MILLISECONDS = 10000;

    public StyleService(StyleRepository styleRepository) {

        this.styleRepository = styleRepository;
        pythonPath = "C:/Anaconda/python.exe";
        String scriptPath = "C:/study/spring/demo/Actual_Model_ver0/service_python.py";
//        ProcessBuilder processBuilder = new ProcessBuilder(List.of(pythonPath, scriptPath));
//        try {
//            this.recommendingProcess = processBuilder.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        this.writer = new BufferedWriter(new OutputStreamWriter(recommendingProcess.getOutputStream()));
//        this.reader = new BufferedReader(new InputStreamReader(recommendingProcess.getInputStream()));
    }

    public List<StyleEntity> getTop8byUser(UserEntity user){
        return styleRepository.findTop8ByUser(user);
    }
    public List<StyleEntity> getTop8(){ return styleRepository.findTop8ByOrderByStyleid(); }

    public StyleEntity getStyleByStyleID(long styleID){ return styleRepository.findByStyleid(styleID); }



//    public CompletableFuture<List<String>> runRecommendingProcess(List<String> args) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                for (String argv : args) {
//                    writer.write(argv);
//                    writer.newLine();
//                }
//                writer.flush();
//
//                List<String> result = new ArrayList<>();
//                long startTime = System.currentTimeMillis();
//                while (System.currentTimeMillis() - startTime < TIMEOUT_MILLISECONDS) {
//                    if (reader.ready()) {
//                        String r1 = reader.readLine();
//                        if (r1 != null) {
//                            result.add(r1);
//                            System.out.println(r1);
//                        }
//                    }
//                }
//                return result;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }


    public List<String> runPythonScript(String scriptPath, List<String> args) {
        List<String> outputLines = new ArrayList<>(); // outputLines 변수 선언

        try {
            List<String> command = new ArrayList<>();
            command.add(pythonPath);
            command.add(scriptPath);
            command.addAll(args);

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 프로세스의 출력을 읽기 위해 BufferedReader 사용
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outputLines.add(line); // 리스트에 각 줄 추가
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputLines; // 리스트 반환
    }

    public boolean saveStyles(List<StyleEntity> styleList){
        styleRepository.saveAll(styleList);
        return true;
    }

    public boolean saveRates(RateStyleDTO rateStyleDTO){
        List<RateStyleDTO.rate> rateList = rateStyleDTO.rateList();

        List<StyleEntity> styleList = new ArrayList<>();
        for(RateStyleDTO.rate r : rateList){
            StyleEntity style = styleRepository.findByStyleid(r.styleId);
            style.setRate(r.rate);
            styleList.add(style);
        }
        styleRepository.saveAll(styleList);

        return true;
    }
}
