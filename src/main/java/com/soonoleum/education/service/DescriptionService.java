package com.soonoleum.education.service;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.soonoleum.education.domain.Description;
import com.soonoleum.education.repository.DescriptionRepositoryImpl;
import com.soonoleum.education.repository.dto.request.ChatGPTRequest;
import com.soonoleum.education.repository.dto.request.DescriptionRequest;
import com.soonoleum.education.repository.dto.response.ChatGPTResponse;
import com.soonoleum.education.repository.dto.response.DescriptionResponse;
import com.soonoleum.education.repository.dto.response.PostResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DescriptionService {

	private final DescriptionRepositoryImpl descriptionRepository;

	@Value("${openai.model}")
	private String model;

	@Value("${openai.api.url}")
	private String apiURL;

	@Autowired
	private RestTemplate template;

	public DescriptionResponse save(final Description description) {
		descriptionRepository.save(description);
		return DescriptionResponse.from(description);
	}

	public DescriptionResponse findById(final Long id) {
		final Description description = descriptionRepository.findById(id);
		return DescriptionResponse.from(description);
	}

	public List<DescriptionResponse> findAll() {
		List<Description> descriptions = descriptionRepository.findAll();
		return DescriptionResponse.fromAll(descriptions);
	}

	public PostResponse generate(final String name,
										final String gender,
										final String number,
										final String region,
										final String keyword,
										final String rawText) {
		// 할머니/할아버지 변환
		final String convert = convertGender(gender);
		// 타이틀 생성
		final String title = generateTitle(name, convert, keyword);
		// fullDescription 생성
		final String description = generateDescription(name, convert, region, keyword, rawText);
		// summary 생성
		final String summary = generateSummary(description);
		// quiz 생성
		final String quizResult = generateQuiz(description);

		// quiz 포메팅
		final DescriptionRequest request =
				quizFormatting(quizResult, name, gender, number, region, title, keyword, rawText, description, summary);

		// 이야기 저장
		final DescriptionResponse response = save(request.toDescription());

		// 저장된 Id 반환
		return PostResponse.from(response.id());
	}

	private String convertGender(final String gender) {
		if (gender.equals("남")) {
			return "할아버지";
		}
		return "할머니";
	}

	private String generateTitle(final String name,
								 final String gender,
								 final String keyword) {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(" ").append(gender).append("의 ").append(keyword);
		return sb.toString();
	}

	private String generateDescription(final String name,
										  final String gender,
										  final String region,
										  final String keyword,
										  final String rawText) {
		StringBuilder sb = new StringBuilder();
		sb.append("이름: ").append(name).append("\n");
		sb.append("성별: ").append(gender).append("\n");
		sb.append("지역: ").append(region).append("\n");
		sb.append("키워드: ").append(keyword).append("\n");
		sb.append("텍스트: 애월읍의 곽지해변은 아름다운 해변과 풍성한 자연경관으로 유명해,")
				.append(" 어린 시절, 바다에서 온 신비한 존재들이 마을 사람들에게 10년 후의 미래를 알려줬어.")
				.append("\n");

		sb.append("작성 조건").append("\n")
				.append("1. 주어지는 '이름','성별','지역','키워드','텍스트'를 기반으로 작성할 것.").append("\n")
				.append("2. 공백 포함 최대 200자, 기승전결 구조를 갖추어 작성할 것.").append("\n")
				.append("3. 주어지는 이름을 주인공으로 할 것").append("\n")
				.append("4. 어린아이의 입장에서 교육적인 콘텐츠로 사용할 수 있을 것.").append("\n");

		sb.append("구성").append("\n")
				.append("1. 동화 형식의 내용으로 작성할 것.").append("\n")
				.append("2. 최대한 사실적으로 구성할 것.").append("\n");

		final String fullDescriptionPrompt = sb.toString();
		return connectGPT(fullDescriptionPrompt);
	}

	private String generateSummary(final String description) {
		StringBuilder sb = new StringBuilder();
		sb.append("텍스트: ").append(description).append("\n");

		sb.append("작성 조건").append("\n")
				.append("1. 주어지는 텍스트를 기반으로 작성할 것.").append("\n")
				.append("2. 공백 포함 최대 70자로 요약, 기승전결 구조를 갖추어 작성할 것.").append("\n")
				.append("3. 어린아이의 입장에서 교육적인 콘텐츠로 사용할 수 있을 것.").append("\n");

		final String summaryPrompt = sb.toString();
		return connectGPT(summaryPrompt);
	}

	private String generateQuiz(final String description) {
		StringBuilder sb = new StringBuilder();
		sb.append("텍스트: ").append(description).append("\n");

		sb.append("작성 조건").append("\n")
				.append("1. 주어지는 텍스트를 기반으로 작성할 것.").append("\n")
				.append("2. 어린아이의 입장에서 교육적인 콘텐츠로 사용할 수 있을 것.").append("\n");

		sb.append("구성").append("\n")
				.append("1. 제주의 지역적 정보를 기반으로 간단한 퀴즈를 만들 것.").append("\n")
				.append("2. 퀴즈는 하나만 생성하고 아래의 구조를 따를 것.").append("\n")

				.append("[질문] ").append("\n")
				.append("[1번] ").append("\n")
				.append("[2번] ").append("\n")
				.append("[3번] ").append("\n")
				.append("[4번] ").append("\n")
				.append("[정답] ").append("\n")

				.append("3. 각 구조는 한줄로 출력하고, 정답에는 번호만 포함할 것.").append("\n")
				.append("4. 질문과 답변은 최대한 짧게 만들 것.").append("\n");

		final String quizPrompt = sb.toString();
		return connectGPT(quizPrompt);
	}

	private DescriptionRequest quizFormatting(final String generateQuizResult,
								final String name,
								final String gender,
								final String number,
								final String region,
								final String title,
								final String keyword,
								final String rawText,
								final String description,
								final String summary) {
		StringTokenizer st = new StringTokenizer(generateQuizResult, "\n");
		final String rawQuiz = st.nextToken();
		final String quiz = rawQuiz.substring(5, rawQuiz.length() - 2);

		final String rawOne = st.nextToken();
		final String one = rawOne.substring(5, rawOne.length() - 2);

		final String rawTwo = st.nextToken();
		final String two = rawTwo.substring(5, rawTwo.length() - 2);

		final String rawThree = st.nextToken();
		final String three = rawThree.substring(5, rawThree.length() - 2);

		final String rawFour = st.nextToken();
		final String four = rawFour.substring(5, rawFour.length() - 2);

		final String rawAnswer = st.nextToken();
		final Integer answer = Integer.parseInt(rawAnswer.substring(5))-1;

		final List<String> options = Arrays.asList(one, two, three, four);

		final DescriptionRequest request =
				new DescriptionRequest(
						name, gender, number, region, title, keyword, rawText, summary, description, quiz, options, answer
				);

		return request;
	}

	private String connectGPT(String prompt) {
		ChatGPTRequest request = new ChatGPTRequest(model, prompt);
		ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
		return chatGPTResponse.getChoices().get(0).getMessage().getContent();
	}
}
