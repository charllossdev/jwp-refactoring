package kitchenpos.acceptance.product.ui;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitchenpos.acceptance.product.ProductAcceptance;
import kitchenpos.product.application.ProductService;
import kitchenpos.product.dto.ProductRequest;
import kitchenpos.product.dto.ProductResponse;
import kitchenpos.product.ui.ProductRestController;

@DisplayName("상품 Controller 테스트")
@WebMvcTest(ProductRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ProductRestControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private ProductService productService;

	@DisplayName("상품을 상품목록에 등록한다.")
	@Test
	void addProductTest() throws Exception {
		// given
		ProductRequest request = ProductRequest.of("치킨", 20000L);
		ProductResponse response = ProductResponse.of(1L, "치킨", 20000L);
		given(productService.create(any())).willReturn(response);

		// when
		final ResultActions resultActions = mvc.perform(post(ProductAcceptance.PRODUCT_REQUEST_URL)
			.content(mapper.writeValueAsString(request))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(redirectedUrl(ProductAcceptance.PRODUCT_REQUEST_URL + "/" + response.getId()))
			.andExpect(jsonPath("$.name").value(request.getName()))
			.andDo(log());
	}

	@DisplayName("등록된 상품의 목록을 조회한다.")
	@Test
	void getProductListTest() throws Exception {
		// given
		given(productService.list()).willReturn(Arrays.asList(
			ProductResponse.of(1L, "치킨", 10000L),
			ProductResponse.of(2L, "피자", 20000L)
		));

		// when
		final ResultActions resultActions = mvc.perform(get(ProductAcceptance.PRODUCT_REQUEST_URL)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].name").value("치킨"))
			.andExpect(jsonPath("$[1].name").value("피자"))
			.andExpect(jsonPath("$..price").isNotEmpty())
			.andDo(log());
	}
}
