package kitchenpos.acceptance.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.acceptance.util.AcceptanceTest;
import kitchenpos.product.dto.ProductRequest;
import kitchenpos.product.dto.ProductResponse;

public class ProductAcceptance extends AcceptanceTest {

	public static final String PRODUCT_REQUEST_URL = "/api/products";

	public static ExtractableResponse<Response> 상품_등록되어_있음(String name, long price) {
		return 상품_등록_요청(ProductRequest.of(name, price));
	}

	public static ExtractableResponse<Response> 상품_등록_요청(ProductRequest request) {
		return RestAssured
			.given().log().all()
			.body(request)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().post(PRODUCT_REQUEST_URL)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 상품_조회_요청() {
		return RestAssured
			.given().log().all()
			.when().get(PRODUCT_REQUEST_URL)
			.then().log().all().extract();
	}

	public static void 상품_등록됨(ExtractableResponse<Response> response) {
		ProductResponse expected = response.as(ProductResponse.class);
		assertAll(
			() -> assertThat(expected).isNotNull(),
			() -> assertThat(expected.getId()).isNotNull(),
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(response.header("Location")).isEqualTo(PRODUCT_REQUEST_URL + "/" + expected.getId())
		);
	}

	public static void 상품_목록_조회됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	public static void 상품_목록_포함됨(ExtractableResponse<Response> response, List<ExtractableResponse<Response>> expected) {

		List<Long> expectedProductIds = expected.stream()
			.map(it -> it.as(ProductResponse.class).getId())
			.collect(Collectors.toList());

		List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
			.map(ProductResponse::getId)
			.collect(Collectors.toList());

		assertThat(resultProductIds).containsAll(expectedProductIds);
	}

	public static void 상품_등록_실패됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
}
