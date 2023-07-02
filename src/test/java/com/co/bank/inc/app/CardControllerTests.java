package com.co.bank.inc.app;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.co.bank.inc.app.exceptions.ResourceBadRequestException;
import com.co.bank.inc.app.models.DTOs.CardDTO;
import com.co.bank.inc.app.services.ICardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTests {

	private static final String END_POINT_PATH = "/card/";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private ICardService cardService;

	@Test
	public void testGenerateCardReturn400ProductIdLessThan6Digits() throws Exception {

		String productId = "52634";
		
		doThrow(ResourceBadRequestException.class).when(cardService).saveCard(productId);

		mockMvc.perform(get(END_POINT_PATH + productId + "/number").contentType("application/json"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.success").value(false))
				.andDo(print());
	}

	@Test
	public void testGenerateCardReturn400ProductIdOnlyNumber() throws Exception {

		String productId = "526A34";
		
		doThrow(ResourceBadRequestException.class).when(cardService).saveCard(productId);

		mockMvc.perform(get(END_POINT_PATH + productId + "/number").contentType("application/json"))
				.andExpect(status().isBadRequest()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.description").value("uri=/card/" + productId + "/number")).andDo(print());
	}

	
	@Test
	public void testGenerateCardReturn201() throws Exception {

		String productId = "526834";

		CardDTO dto = new CardDTO();
		dto.setState("INACTIVE");
		dto.setCardId(productId.concat("4758963214"));

		when(cardService.saveCard(productId)).thenReturn(dto);

		mockMvc.perform(get(END_POINT_PATH + productId + "/number").contentType("application/json"))
				.andExpect(status().isCreated()).andDo(print());
	}

	
	/*@Test
	public void testGenerateCardReturn400BadRequest() throws Exception {
		CommonBodyDTO bodyDTO = new CommonBodyDTO("14523");

		mockMvc.perform(post(END_POINT_PATH + "/enroll").contentType("application/json")
				.content(objectMapper.writeValueAsString(bodyDTO))).andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").value("cardId, must have 16 digits and only numbers.")).andDo(print());
	}*/

}
