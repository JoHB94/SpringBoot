package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
/*ResqustMapping 어노테이션은 별다른 설정을 하지 않으면 HTTP의 모든 요청을 받는다. */
public class GetController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String getHello(){
        return "Hello World";
    }
    /*Spring 4.3버전 이후로는 @RequestMapping어노테이션을 더이상 사용하지 않는다.
    * 각 HTTP메서드에 맞는 어노테이션을 사용한다.
    * @GetMapping
    * @PostMapping
    * PutMapping
    * DeleteMapping
    * */

    /*매개변수를 받지 않는 GET메서드 구현*/
    @GetMapping(value = "/name")
    public String getName(){
        return "Flature";
    }

    /*실무 환경에서는 매개변수를 받지 않는 메서드가 거의 쓰이지 않습니다.
    * PathVariable 어노테이션을 사용한 매개변수 이용하기*/
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable){
        return variable;
    }

    /*어노테이션에 지정한 변수 이름과 메서드의 매개변수 이름을 맞추기 어려운 경우*/
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable") String var){
        return var;
    }

    /*RequestParam 어노테이션을 활용한 매개변수 이용하기*/
    @Operation(summary = "GET 메서드 예제",description = "@RequestParam을 활용한 GET Method")
    @GetMapping(value = "/request1")
    public String getRequestParam1(
            @Parameter(description = "이름",required = true) @RequestParam String name,
            @Parameter(description = "이메일",required = true)@RequestParam String email,
            @Parameter(description = "회사",required = true)@RequestParam String organization) {
        return name + " " + email + " " + organization;
    }

    /*만약 쿼리스트링에 들어가야 할 값이 미정이라면 Map객체를 활용할 수도 있습니다.
    * Map.entrySet() : map객체의 모든 값을 출력*/
    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String,String> param){
        StringBuilder sb = new StringBuilder();
        param.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });
        return sb.toString();
    }

    /*DTO객체를 이용한 GET메서드 구현*/
    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto memberDto){
        return memberDto.toString();
    }




}
