package com.bbva.rbvd.lib.r415;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;

import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.pisd.lib.r401.PISDR401;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.commons.DocumentTypeDTO;
import com.bbva.rbvd.dto.insrncsale.commons.IdentityDocumentDTO;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.mock.MockData;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantTypeDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r047.RBVDR047;
import com.bbva.rbvd.lib.r415.impl.RBVDR415Impl;
import com.bbva.rbvd.lib.r415.impl.business.ICR2Business;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.bbva.pisd.dto.insurance.utils.PISDConstants.CHANNEL_GLOMO;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/RBVDR415-app.xml",
		"classpath:/META-INF/spring/RBVDR415-app-test.xml",
		"classpath:/META-INF/spring/RBVDR415-arc.xml",
		"classpath:/META-INF/spring/RBVDR415-arc-test.xml" })
public class RBVDR415Test {


	private RBVDR415Impl rbvdr415;

	private PISDR012 pisdR012;

	private PISDR401 pisdR401;

	private RBVDR047 rbvdR047;


	private PISDR226 pisdR226;

	private PolicyDTO requestBody;

	private ICR2Response icr2Response;

	private RequiredFieldsEmissionDAO emissionDao;

	private ICR2Business icr2Helper;

	private MockData mockData;


	@Before
	public void setUp() throws Exception {

		rbvdr415 = new RBVDR415Impl();

		pisdR012 = Mockito.mock(PISDR012.class);
		pisdR226 = Mockito.mock(PISDR226.class);
		pisdR401 = Mockito.mock(PISDR401.class);
		rbvdR047 = Mockito.mock(RBVDR047.class);
		ApplicationConfigurationService applicationConfigurationService = Mockito.mock(ApplicationConfigurationService.class);


		rbvdr415.setPisdR226(pisdR226);
		rbvdr415.setPisdR012(pisdR012);
		rbvdr415.setRbvdR047(rbvdR047);
		rbvdr415.setPisdR401(pisdR401);
		rbvdr415.setApplicationConfigurationService(applicationConfigurationService);

		mockData = MockData.getInstance();

		requestBody = mockData.createRequestPreFormalizationTrx();

		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_TLMKT_CODE)).thenReturn("7794");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_PIC_CODE)).thenReturn("PC");
		when(applicationConfigurationService.getProperty(CHANNEL_GLOMO)).thenReturn("13000013");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_CONTACT_CENTER_CODE)).thenReturn("CC");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.CHANNEL_CONTACT_DETAIL)).thenReturn("13000013");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_AGENT_PROMOTER_CODE)).thenReturn("UCQGSPPP");

		when(pisdR226.executeFindQuotationIfExistInContract(Mockito.anyString())).thenReturn(false);

		PaymentPeriodEntity paymentPeriodEntity = new PaymentPeriodEntity();
		paymentPeriodEntity.setPaymentFrequencyId(new BigDecimal(3));
		paymentPeriodEntity.setPaymentFrequencyName("ANUAL");
		when(pisdR226.executeFindPaymentPeriodByType(Mockito.anyString())).thenReturn(paymentPeriodEntity);

		Map<String, Object> responseQueryGetRequiredFields = new HashMap<>();
		responseQueryGetRequiredFields.put(RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue(), new BigDecimal(13));
		responseQueryGetRequiredFields.put(PISDProperties.FIELD_INSURANCE_PRODUCT_DESC.getValue(),"Insurance Product Description");
		responseQueryGetRequiredFields.put(RBVDProperties.FIELD_CONTRACT_DURATION_NUMBER.getValue(),999);
		responseQueryGetRequiredFields.put(RBVDProperties.FIELD_CONTRACT_DURATION_TYPE.getValue(),"");
		when(pisdR012.executeGetASingleRow(
				RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(),
				Collections.singletonMap(RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue(),requestBody.getQuotationNumber())))
				.thenReturn(responseQueryGetRequiredFields);

		icr2Response = new ICR2Response();
		ICMRYS2 icmrys2 = new ICMRYS2();
		icmrys2.setOFICON("1234");
		icmrys2.setNUMCON("00110482734000098127");
		icr2Response.setIcmrys2(icmrys2);
		when(rbvdR047.executePolicyRegistration(Mockito.anyObject())).thenReturn(icr2Response);

		when(pisdR226.executeInsertInsuranceContract(Mockito.anyMap())).thenReturn(1);
	}

	@Test
	public void executeLogicPreFormalization_TestSucessFlowVidaLey(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,3,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Responsable de pago empresa
		requestBody.getParticipants().get(0).getIdentityDocument().getDocumentType().setId("RUC");
		requestBody.getParticipants().get(0).getIdentityDocument().setNumber("20479438413");
		requestBody.getParticipants().get(0).setCustomerId("97165552");

		//Se agrega 2 representantes legales
		ParticipantDTO legal1 = mockCreateParticipant("DNI","37850934","78122201","LEGAL_REPRESENTATIVE");
		requestBody.getParticipants().add(legal1);
		ParticipantDTO legal2 = mockCreateParticipant("DNI","20009971","73399927","LEGAL_REPRESENTATIVE");
		requestBody.getParticipants().add(legal2);

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
	}

	@Test
	public void executeLogicPreFormalization_TestParticipantsWithBeneficiary() throws IOException {
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,6,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Se agrega beneficiarios de otro mock
		PolicyDTO request2WithBeneficiaries = mockData.getCreateInsuranceRequestBody();
		requestBody.setParticipants(request2WithBeneficiaries.getParticipants());

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
	}

	@Test
	public void executeLogicPreFormalization_TestParticipantsWithInsured(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,2));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		icr2Response.getIcmrys2().setOFICON("7794");
		when(rbvdR047.executePolicyRegistration(Mockito.anyObject())).thenReturn(icr2Response);

		//Se agrega asegurado
		ParticipantDTO insured = mockCreateParticipant("FOREIGNERS","97793201","69503241210","INSURED");
		requestBody.getParticipants().add(insured);

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
	}

	@Test
	public void executeLogicPreFormalization_TestParticipantsWithEndorsee(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,2));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Se agrega endosatario
		ParticipantDTO endorsee = mockCreateParticipant("RUC","20224043","20628447889","ENDORSEE");
		endorsee.setBenefitPercentage(100D);
		requestBody.getParticipants().add(endorsee);

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
	}


	@Test(expected = BusinessException.class)
	public void executeLogicPreFormalization_TestQuotationExist(){

		when(pisdR226.executeFindQuotationIfExistInContract(Mockito.anyString())).thenReturn(true);

		rbvdr415.executeLogicPreFormalization(requestBody);


		// Verificar que los métodos posteriores no se ejecutan
		verify(pisdR226, never()).executeFindPaymentPeriodByType(any());
		verify(rbvdR047, never()).executePolicyRegistration(any());
	}

	@Test(expected = BusinessException.class)
	public void executeLogicPreFormalization_TestQuotationInvalid(){
		when(pisdR012.executeGetASingleRow(
				RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(),
				Collections.singletonMap(RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue(),requestBody.getQuotationNumber())))
				.thenReturn(Collections.emptyMap());

		rbvdr415.executeLogicPreFormalization(requestBody);

		// Verificar que los métodos posteriores no se ejecutan
		verify(pisdR226, never()).executeFindPaymentPeriodByType(any());
		verify(rbvdR047, never()).executePolicyRegistration(any());
	}

	private ParticipantDTO mockCreateParticipant(String document,String customerId, String documentNumber,
												 String rol){
		ParticipantDTO participant = new ParticipantDTO();

		IdentityDocumentDTO identityDocument = new IdentityDocumentDTO();
		DocumentTypeDTO documentType = new DocumentTypeDTO();
		documentType.setId(document);
		identityDocument.setDocumentType(documentType);
		identityDocument.setNumber(documentNumber);
		participant.setIdentityDocument(identityDocument);

		ParticipantTypeDTO participantType = new ParticipantTypeDTO();
		participantType.setId(rol);
		participant.setParticipantType(participantType);

		participant.setCustomerId(customerId);

		return participant;
	}

	private List<Map<String, Object>> mockGetRolesFromDB(List<Integer> roles){
		List<Map<String, Object>> rolesFromDB = new ArrayList<>();

		for(int numRol: roles){
			Map<String, Object> rol = new HashMap<>();
			rol.put("PARTICIPANT_ROLE_ID",numRol);
			rolesFromDB.add(rol);
		}

		return rolesFromDB;
	}
	
}
