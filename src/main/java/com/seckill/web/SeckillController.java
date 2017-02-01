package com.seckill.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.domain.Seckill;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatkillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;

@Controller//把Controller放入Spring容器中
@RequestMapping("/seckill")// url:/模块/资源/{id}/细分
public class SeckillController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SeckillService seckillService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		//获取列表页
		List<Seckill> list = seckillService.getSeckills();
		model.addAttribute("list", list);
		//list.jpg + model = ModelView
		return "list";// /WEB-INF/jsp/"list".jsp
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId,Model model){
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getSeckillById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";// /WEB-INF/jsp/"detail".jsp
	}
	
	@RequestMapping(value = "/{seckillId}/exposer",
					method = RequestMethod.POST,
			        produces = {"application/json;charset=UTF-8"}
			)
	@ResponseBody
	public SeckillResult<Exposer> exposer(Long seckillId){
		SeckillResult<Exposer> result;
		try{
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/{seckillId}/{md5}/execution",
					method = RequestMethod.POST,
					produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execution(@PathVariable("seckillId") Long seckillId,
													 @PathVariable("md5") String md5,
													 @CookieValue(value = "killPhone", required = false) Long userphone){
		if (userphone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		try {
			SeckillExecution execution = seckillService.excuteSeckill(seckillId, userphone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch(RepeatkillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(false, execution);
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(false, execution);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(false, execution);
		}
		}
	
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	public SeckillResult<Long> time(){
		Date date = new Date();
		return new SeckillResult<Long>(true, date.getTime());
	}
}
