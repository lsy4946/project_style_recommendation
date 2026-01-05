package faceweb.demo;

import org.python.core.PyList;
import org.python.core.PyObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static File convert(MultipartFile multipartFile) throws IOException {
        // MultiPartFile에서 InputStream을 가져옵니다.
        InputStream inputStream = multipartFile.getInputStream();

        // File 객체 생성
        File file = new File(multipartFile.getOriginalFilename());

        // InputStream에서 파일로 데이터를 복사합니다.
        copyInputStreamToFile(inputStream, file);

        return file;
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            inputStream.close();
        }
    }

    // PyObject를 Java List로 변환하는 메서드
    public static List<String> convertPyObjectToList(PyObject pyList) {
        List<String> list = new ArrayList<>();
        if (pyList instanceof PyList) {
            PyList pyListItems = (PyList) pyList;
            for (Object item : pyListItems) {
                list.add(item.toString());
            }
        }
        return list;
    }

    public static String pathConverter(String absolutePath){
        // 역슬래시를 슬래시로 변경
        String unixStylePath = absolutePath.replace("\\", "/");

        // "demo" 이하의 텍스트 삭제
        int index = unixStylePath.indexOf("/demo/");
        if (index != -1) {
            unixStylePath = unixStylePath.substring(index + "/demo/".length());
        }

        return unixStylePath;
    }

    public static List<String> pathListConverter(List<String> absolutePathList){

        List<String> relativePathList = new ArrayList<>();

        for(String absolutePath : absolutePathList){
            relativePathList.add(pathConverter(absolutePath));
        }

        return relativePathList;
    }
}