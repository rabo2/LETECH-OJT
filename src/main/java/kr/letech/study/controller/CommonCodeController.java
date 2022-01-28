package kr.letech.study.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.letech.study.service.CommonCodeService;
import kr.letech.study.vo.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/commonCode")
@Slf4j
public class CommonCodeController {

	@Autowired
	private CommonCodeService cmnCdService;

	@RequestMapping("")
	public String commonCodePage() {
		return "commonCode/main";
	}

	@GetMapping("list/{comnCd}")
	public String commonCodeList(Model model, @PathVariable(required = false) String comnCd) throws Exception {
		String target = "";
		
		if(comnCd.equals("undefined")) {
			List<CommonCode> cmdList = cmnCdService.getCommonCodeList();
			model.addAttribute("cmdList", cmdList);
			target = "commonCode/main :: #codeList";
			
		}else {
			CommonCode code = cmnCdService.getCommonCode(comnCd);
			model.addAttribute("code", code);
			target = "commonCode/main :: #detailCode";
		}
			
		return target;
	}
	
	@GetMapping("/upCode")
	public String commonCodeListByUpCd(Model model, @RequestParam Map<String, String> paraMap) throws Exception{
		log.info(">>>>>>>>>>>>{}", paraMap.get("target"));
		
		model.addAttribute("codeList",cmnCdService.getCommonCodeListByUpCode(paraMap));
		
		return paraMap.get("target");
	}
	
	

	@PostMapping("/regist")
	public String registCommonCode(Model model, CommonCode cmd) throws Exception {
		cmnCdService.registCommonCode(cmd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList", cmdList);

		return "commonCode/main :: #codeList";
	}

	@PutMapping("/modify")
	public String modifyCommonCode(Model model, CommonCode cmd) throws Exception {
		cmnCdService.modifyCommandCode(cmd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList", cmdList);

		return "commonCode/main :: #codeList";
	}

	@Delete("/remove/{comnCd}")
	public String remonveCommonCode(Model model, @PathVariable String comnCd) throws Exception {
		cmnCdService.removeCommonCode(comnCd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList", cmdList);

		return "commonCode/main :: #codeList";
	}
}