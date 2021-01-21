package kitchenpos.ui;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitchenpos.acceptance.product.ProductAcceptanceTemp;
import kitchenpos.application.ProductServiceDelete;
import kitchenpos.domain.Product;

@DisplayName("상품 Controller 테스트")
@WebMvcTest(ProductRestControllerDelete.class)
class ProductRestControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private ProductServiceDelete productServiceDelete;

	@DisplayName("상품을 상품목록에 등록한다.")
	@Test
	void addProductTest() throws Exception {
		// given
		Product newProduct = Product.of(1L, "치킨", 20000L);
		given(productServiceDelete.create(any())).willReturn(newProduct);

		// when
		final ResultActions resultActions = mvc.perform(post(ProductAcceptanceTemp.PRODUCT_REQUEST_URL)
			.content(mapper.writeValueAsString(newProduct))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(redirectedUrl(ProductAcceptanceTemp.PRODUCT_REQUEST_URL + "/" + newProduct.getId()))
			.andExpect(jsonPath("$.name").value(newProduct.getName()))
			.andDo(log());
	}

	@DisplayName("등록된 상품의 목록을 조회한다.")
	@Test
	void getProductListTest() throws Exception {
		// given
		given(productServiceDelete.list()).willReturn(Arrays.asList(
			Product.of(1L, "치킨", 10000L),
			Product.of(2L, "피자", 20000L)
		));

		// when
		final ResultActions resultActions = mvc.perform(get(ProductAcceptanceTemp.PRODUCT_REQUEST_URL)
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
