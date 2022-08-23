package kr.human.ISP.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileVO {
    private String uuid;		// unique 한 파일 이름을 만들기 위한 속성
    private String fileName;	// 실제 파일 이름
    private String contentType;	// 파일 종류
}