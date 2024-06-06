package com.bbva.rbvd.lib.r415;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.domain.transaction.Context;
import com.bbva.elara.domain.transaction.ThreadContext;
import com.bbva.elara.utility.api.connector.APIConnector;
import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.pisd.lib.r401.PISDR401;
import com.bbva.pisd.lib.r601.PISDR601;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICMRYS3;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Response;
import com.bbva.rbvd.dto.cicsconnection.utils.HostAdvice;
import com.bbva.rbvd.dto.insrncsale.commons.*;
import com.bbva.rbvd.dto.insrncsale.mock.MockData;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantTypeDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.dto.preformalization.util.RBVDMessageError;
import com.bbva.rbvd.lib.r415.factory.ApiConnectorFactoryTest;
import com.bbva.rbvd.lib.r415.impl.RBVDR415Impl;
import com.bbva.rbvd.lib.r602.RBVDR602;
import com.bbva.rbvd.mock.MockBundleContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.bbva.pisd.dto.insurance.utils.PISDConstants.CHANNEL_GLOMO;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/RBVDR415-app.xml",
		"classpath:/META-INF/spring/RBVDR415-app-test.xml",
		"classpath:/META-INF/spring/RBVDR415-arc.xml",
		"classpath:/META-INF/spring/RBVDR415-arc-test.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RBVDR415Test {

	@Spy
	private Context context;

	private final RBVDR415Impl rbvdr415 = new RBVDR415Impl();;

	private PISDR012 pisdR012;

	private PISDR401 pisdR401;


	private PISDR601 pisdr601;

	private PISDR226 pisdR226;
	private RBVDR602 rbvdR602;

	private PolicyDTO requestBody;

	private ICR3Response icr3Response;

	private MockData mockData;
	private ApplicationConfigurationService applicationConfigurationService;
	private APIConnector internalApiConnectorImpersonation;
	private Map<String,Object> quotationInfo;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		context = new Context();
		ThreadContext.set(context);

		pisdR012 = Mockito.mock(PISDR012.class);
		pisdR226 = Mockito.mock(PISDR226.class);
		pisdR401 = Mockito.mock(PISDR401.class);
		pisdr601 = Mockito.mock(PISDR601.class);
		rbvdR602 = Mockito.mock(RBVDR602.class);
		applicationConfigurationService = Mockito.mock(ApplicationConfigurationService.class);

		MockBundleContext mockBundleContext = mock(MockBundleContext.class);
		ApiConnectorFactoryTest apiConnectorFactoryMock = new ApiConnectorFactoryTest();
		internalApiConnectorImpersonation = apiConnectorFactoryMock.getAPIConnector(mockBundleContext, true, true);

		rbvdr415.setPisdR226(pisdR226);
		rbvdr415.setPisdR012(pisdR012);
		rbvdr415.setPisdR401(pisdR401);
		rbvdr415.setPisdR601(pisdr601);
		rbvdr415.setRbvdR602(rbvdR602);
		rbvdr415.setApplicationConfigurationService(applicationConfigurationService);
		rbvdr415.setInternalApiConnectorImpersonation(internalApiConnectorImpersonation);

		mockData = MockData.getInstance();

		requestBody = mockData.createRequestPreFormalizationTrx();

		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_TLMKT_CODE)).thenReturn("7794");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_PIC_CODE)).thenReturn("PC");
		when(applicationConfigurationService.getProperty(CHANNEL_GLOMO)).thenReturn("13000013");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_CONTACT_CENTER_CODE)).thenReturn("CC");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.CHANNEL_CONTACT_DETAIL)).thenReturn("13000013");
		when(applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_AGENT_PROMOTER_CODE)).thenReturn("UCQGSPPP");
		when(applicationConfigurationService.getDefaultProperty("flag.callevent.createinsured.for.preemision","N")).thenReturn("N");
		when(applicationConfigurationService.getProperty("DNI")).thenReturn("L");
		when(applicationConfigurationService.getProperty("PASSPORT")).thenReturn("P");

		when(pisdR226.executeFindQuotationIfExistInContract(Mockito.anyString())).thenReturn(false);

		PaymentPeriodEntity paymentPeriodEntity = new PaymentPeriodEntity();
		paymentPeriodEntity.setPaymentFrequencyId(new BigDecimal(3));
		paymentPeriodEntity.setPaymentFrequencyName("ANUAL");
		when(pisdR226.executeFindPaymentPeriodByType(Mockito.anyString())).thenReturn(paymentPeriodEntity);

		quotationInfo = new HashMap<>();
		quotationInfo.put("INSURANCE_BUSINESS_NAME","VIDA");
		quotationInfo.put("INSURANCE_PRODUCT_ID",13);
		quotationInfo.put("INSURANCE_PRODUCT_DESC","Seguro Vida Ley");
		quotationInfo.put("CONTRACT_DURATION_TYPE","");
		quotationInfo.put("CONTRACT_DURATION_NUMBER",999);
		quotationInfo.put("INSURANCE_MODALITY_NAME","PLAN PLATA");
		quotationInfo.put("INSURANCE_COMPANY_QUOTA_ID","ba3c7d41-65ce-4582-bde2-08f21311fbc9");
		when(pisdr601.executeFindQuotationDetailForPreEmision(requestBody.getQuotationNumber()))
				.thenReturn(quotationInfo);

		icr3Response = new ICR3Response();
		ICMRYS3 icmrys3 = new ICMRYS3();
		icmrys3.setOFICON("1234");
		icmrys3.setNUMCON("00110482734000098127");
		icmrys3.setFECCTR("2024-05-21 14:35:36");
		icmrys3.setFECINI("2024-05-21");
		icmrys3.setFECFIN("2025-05-21");
		icr3Response.setIcmrys3(icmrys3);
		when(rbvdR602.executePreFormalizationInsurance(Mockito.anyObject())).thenReturn(icr3Response);

		when(pisdR226.executeInsertInsuranceContract(Mockito.anyMap())).thenReturn(1);
	}

	/*
	CASO 1: PRODUCTO VIDA LEY CON UNA CUENTA, CLIENTE RUC 20, PLAN 01, 1 REPRESENTANTE LEGAL
	 */
	@Test
	public void executeTestFlowLifeLawCase1(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,3,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Responsable de pago empresa ruc 20
		requestBody.getParticipants().get(0).getIdentityDocument().getDocumentType().setId("RUC");
		requestBody.getParticipants().get(0).getIdentityDocument().setNumber("20479438413");
		requestBody.getParticipants().get(0).setCustomerId("97165552");

		//Se agrega 1 representante legal
		ParticipantDTO legal1 = mockCreateParticipant("DNI","37850934","78122201","LEGAL_REPRESENTATIVE");
		requestBody.getParticipants().add(legal1);

		//producto vida ley
		requestBody.getProduct().setId("842");

		//plan 01
		requestBody.getProduct().getPlan().setId("01");

		//cuenta de cliente
		requestBody.getRelatedContracts().get(0).getContractDetails().getProductType().setId("ACCOUNT");
		requestBody.getRelatedContracts().get(0).getContractDetails().setNumber("00110130260299972507");

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1});

		when(applicationConfigurationService.getDefaultProperty("flag.callevent.createinsured.for.preemision","N")).thenReturn("S");
		when(this.internalApiConnectorImpersonation.exchange(anyString(), any(HttpMethod.class), anyObject(),
				(Class<Integer>)any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

		PolicyInspectionDTO inspection = new PolicyInspectionDTO();

		inspection.setIsRequired(true);
		inspection.setFullName("Cristian Alexis Segovia Farfan");

		List<ContactDetailDTO> contactDetails = new ArrayList<>();
		ContactDetailDTO phone = new ContactDetailDTO();
		phone.setContact(new ContactDTO());
		phone.getContact().setContactDetailType("PHONE");
		phone.getContact().setPhoneNumber("98736442");
		contactDetails.add(phone);
		ContactDetailDTO email = new ContactDetailDTO();
		email.setContact(new ContactDTO());
		email.getContact().setContactDetailType("EMAIL");
		email.getContact().setAddress("cristian.segovia.contractor@bbva.com");
		contactDetails.add(email);
		inspection.setContactDetails(contactDetails);
		requestBody.setInspection(inspection);
		requestBody.setHeaderOperationDate("2024-05-21");
		requestBody.setHeaderOperationTime("14:35:36");

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(2,validation.getParticipants().size());

		//Verifica que llame a las librerias externas y el internal api connector
		verify(pisdR226, times(1)).executeFindQuotationIfExistInContract(any());
		verify(pisdr601, times(1)).executeFindQuotationDetailForPreEmision(any());
		verify(pisdR012, times(1)).executeGetRolesByProductAndModality(any(),any());
		verify(pisdR226, times(1)).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, times(1)).executePreFormalizationInsurance(any());
		verify(pisdR012, times(1)).executeMultipleInsertionOrUpdate(any(),any());
		verify(internalApiConnectorImpersonation, times(1)).exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any());
	}


	/*
	CASO 2: PRODUCTO VIDA LEY CON UNA CUENTA, CLIENTE RUC 20, PLAN 02, 4 REPRESENTANTES LEGALES
	 */
	@Test
	public void executeTestFlowLifeLawCase2(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,3,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Responsable de pago empresa ruc 20
		requestBody.getParticipants().get(0).getIdentityDocument().getDocumentType().setId("RUC");
		requestBody.getParticipants().get(0).getIdentityDocument().setNumber("20479438413");
		requestBody.getParticipants().get(0).setCustomerId("97165552");

		//Se agrega 4 representantes legales
		ParticipantDTO legal1 = mockCreateParticipant("DNI","37850934","78122201","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal2 = mockCreateParticipant("DNI","76110922","89001171","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal3 = mockCreateParticipant("PASSPORT","10008677","45553306","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal4 = mockCreateParticipant("DNI","38884932","50009182","LEGAL_REPRESENTATIVE");
		requestBody.getParticipants().add(legal1);
		requestBody.getParticipants().add(legal2);
		requestBody.getParticipants().add(legal3);
		requestBody.getParticipants().add(legal4);

		//producto vida ley
		requestBody.getProduct().setId("842");

		//plan 01
		requestBody.getProduct().getPlan().setId("02");

		//cuenta de cliente
		requestBody.getRelatedContracts().get(0).getContractDetails().getProductType().setId("ACCOUNT");
		requestBody.getRelatedContracts().get(0).getContractDetails().setNumber("00110130260299972507");

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1,1,1,1});

		when(applicationConfigurationService.getDefaultProperty("flag.callevent.createinsured.for.preemision","N")).thenReturn("S");
		when(this.internalApiConnectorImpersonation.exchange(anyString(), any(HttpMethod.class), anyObject(),
				(Class<Integer>)any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

		PolicyInspectionDTO inspection = new PolicyInspectionDTO();

		inspection.setIsRequired(true);
		inspection.setFullName("Cristian Alexis Segovia Farfan");

		List<ContactDetailDTO> contactDetails = new ArrayList<>();
		ContactDetailDTO phone = new ContactDetailDTO();
		phone.setContact(new ContactDTO());
		phone.getContact().setContactDetailType("PHONE");
		phone.getContact().setPhoneNumber("98736442");
		contactDetails.add(phone);
		ContactDetailDTO email = new ContactDetailDTO();
		email.setContact(new ContactDTO());
		email.getContact().setContactDetailType("EMAIL");
		email.getContact().setAddress("cristian.segovia.contractor@bbva.com");
		contactDetails.add(email);
		inspection.setContactDetails(contactDetails);
		requestBody.setInspection(inspection);
		requestBody.setHeaderOperationDate("2024-05-21");
		requestBody.setHeaderOperationTime("14:35:36");

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(5,validation.getParticipants().size());

		//Verifica que llame a las librerias externas y el internal api connector
		verify(pisdR226, times(1)).executeFindQuotationIfExistInContract(any());
		verify(pisdr601, times(1)).executeFindQuotationDetailForPreEmision(any());
		verify(pisdR012, times(1)).executeGetRolesByProductAndModality(any(),any());
		verify(pisdR226, times(1)).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, times(1)).executePreFormalizationInsurance(any());
		verify(pisdR012, times(1)).executeMultipleInsertionOrUpdate(any(),any());
		verify(internalApiConnectorImpersonation, times(1)).exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any());
	}


	/*
	CASO 3: PRODUCTO VIDA LEY CON UNA TARJETA, CLIENTE RUC 20, PLAN 03, 2 REPRESENTANTES LEGALES
	 */

	@Test
	public void executeTestFlowLifeLawCase3(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,3,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Responsable de pago empresa ruc 20
		requestBody.getParticipants().get(0).getIdentityDocument().getDocumentType().setId("RUC");
		requestBody.getParticipants().get(0).getIdentityDocument().setNumber("20479438413");
		requestBody.getParticipants().get(0).setCustomerId("97165552");

		//Se agrega 4 representantes legales
		ParticipantDTO legal1 = mockCreateParticipant("DNI","37850934","78122201","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal2 = mockCreateParticipant("DNI","76110922","89001171","LEGAL_REPRESENTATIVE");
		requestBody.getParticipants().add(legal1);
		requestBody.getParticipants().add(legal2);

		//producto vida ley
		requestBody.getProduct().setId("842");

		//plan 01
		requestBody.getProduct().getPlan().setId("03");

		//tarjeta de cliente
		requestBody.getRelatedContracts().get(0).getContractDetails().getProductType().setId("CARD");
		requestBody.getRelatedContracts().get(0).getContractDetails().setNumber("4919108221879862");

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1,1});

		when(applicationConfigurationService.getDefaultProperty("flag.callevent.createinsured.for.preemision","N")).thenReturn("S");
		when(this.internalApiConnectorImpersonation.exchange(anyString(), any(HttpMethod.class), anyObject(),
				(Class<Integer>)any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

		PolicyInspectionDTO inspection = new PolicyInspectionDTO();

		inspection.setIsRequired(true);
		inspection.setFullName("Cristian Alexis Segovia Farfan");

		List<ContactDetailDTO> contactDetails = new ArrayList<>();
		ContactDetailDTO phone = new ContactDetailDTO();
		phone.setContact(new ContactDTO());
		phone.getContact().setContactDetailType("PHONE");
		phone.getContact().setPhoneNumber("98736442");
		contactDetails.add(phone);
		ContactDetailDTO email = new ContactDetailDTO();
		email.setContact(new ContactDTO());
		email.getContact().setContactDetailType("EMAIL");
		email.getContact().setAddress("cristian.segovia.contractor@bbva.com");
		contactDetails.add(email);
		inspection.setContactDetails(contactDetails);
		requestBody.setInspection(inspection);
		requestBody.setHeaderOperationDate("2024-05-21");
		requestBody.setHeaderOperationTime("14:35:36");

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(3,validation.getParticipants().size());

		//Verifica que llame a las librerias externas y el internal api connector
		verify(pisdR226, times(1)).executeFindQuotationIfExistInContract(any());
		verify(pisdr601, times(1)).executeFindQuotationDetailForPreEmision(any());
		verify(pisdR012, times(1)).executeGetRolesByProductAndModality(any(),any());
		verify(pisdR226, times(1)).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, times(1)).executePreFormalizationInsurance(any());
		verify(pisdR012, times(1)).executeMultipleInsertionOrUpdate(any(),any());
		verify(internalApiConnectorImpersonation, times(1)).exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any());
	}


	/*
	CASO 4: PRODUCTO VIDA LEY CON UNA TARJETA, CLIENTE RUC 20, PLAN 01, 5 REPRESENTANTES LEGALES
	 */

	@Test
	public void executeTestFlowLifeLawCase4(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,3,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//Responsable de pago empresa ruc 20
		requestBody.getParticipants().get(0).getIdentityDocument().getDocumentType().setId("RUC");
		requestBody.getParticipants().get(0).getIdentityDocument().setNumber("20479438413");
		requestBody.getParticipants().get(0).setCustomerId("97165552");

		//Se agrega 4 representantes legales
		ParticipantDTO legal1 = mockCreateParticipant("DNI","37850934","78122201","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal2 = mockCreateParticipant("DNI","76110922","89001171","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal3 = mockCreateParticipant("DNI","80009125","67009815","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal4 = mockCreateParticipant("DNI","10977833","72000822","LEGAL_REPRESENTATIVE");
		ParticipantDTO legal5 = mockCreateParticipant("DNI","10998361","70009818","LEGAL_REPRESENTATIVE");
		requestBody.getParticipants().add(legal1);
		requestBody.getParticipants().add(legal2);
		requestBody.getParticipants().add(legal3);
		requestBody.getParticipants().add(legal4);
		requestBody.getParticipants().add(legal5);

		//producto vida ley
		requestBody.getProduct().setId("842");

		//plan 01
		requestBody.getProduct().getPlan().setId("01");

		//tarjeta de cliente
		requestBody.getRelatedContracts().get(0).getContractDetails().getProductType().setId("CARD");
		requestBody.getRelatedContracts().get(0).getContractDetails().setNumber("4919108221879862");

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1,1});

		when(applicationConfigurationService.getDefaultProperty("flag.callevent.createinsured.for.preemision","N")).thenReturn("S");
		when(this.internalApiConnectorImpersonation.exchange(anyString(), any(HttpMethod.class), anyObject(),
				(Class<Integer>)any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

		PolicyInspectionDTO inspection = new PolicyInspectionDTO();

		inspection.setIsRequired(true);
		inspection.setFullName("Cristian Alexis Segovia Farfan");

		List<ContactDetailDTO> contactDetails = new ArrayList<>();
		ContactDetailDTO phone = new ContactDetailDTO();
		phone.setContact(new ContactDTO());
		phone.getContact().setContactDetailType("PHONE");
		phone.getContact().setPhoneNumber("98736442");
		contactDetails.add(phone);
		ContactDetailDTO email = new ContactDetailDTO();
		email.setContact(new ContactDTO());
		email.getContact().setContactDetailType("EMAIL");
		email.getContact().setAddress("cristian.segovia.contractor@bbva.com");
		contactDetails.add(email);
		inspection.setContactDetails(contactDetails);
		requestBody.setInspection(inspection);
		requestBody.setHeaderOperationDate("2024-05-21");
		requestBody.setHeaderOperationTime("14:35:36");

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(6,validation.getParticipants().size());

		//Verifica que llame a las librerias externas y el internal api connector
		verify(pisdR226, times(1)).executeFindQuotationIfExistInContract(any());
		verify(pisdr601, times(1)).executeFindQuotationDetailForPreEmision(any());
		verify(pisdR012, times(1)).executeGetRolesByProductAndModality(any(),any());
		verify(pisdR226, times(1)).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, times(1)).executePreFormalizationInsurance(any());
		verify(pisdR012, times(1)).executeMultipleInsertionOrUpdate(any(),any());
		verify(internalApiConnectorImpersonation, times(1)).exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any());
	}


	/*
	CASO 5: PRODUCTO VEHICULAR CON UNA CUENTA, CLIENTE DNI, PLAN 01, CON RESPONSABLE DE PAGO DNI
	 */
	@Test
	public void executeTestFlowVehicularCase5(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,2));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//producto vehicular
		requestBody.getProduct().setId("830");

		quotationInfo.put("INSURANCE_BUSINESS_NAME","VEHICULAR");
		quotationInfo.put("INSURANCE_PRODUCT_ID",1);
		quotationInfo.put("INSURANCE_PRODUCT_DESC","Seguro VEHICULAR");
		quotationInfo.put("CONTRACT_DURATION_TYPE","");
		quotationInfo.put("CONTRACT_DURATION_NUMBER",1);
		quotationInfo.put("INSURANCE_MODALITY_NAME","PLAN BASICO");
		quotationInfo.put("INSURANCE_COMPANY_QUOTA_ID","ba3c7d41-65ce-4582-bde2-08f21311fbc9");
		when(pisdr601.executeFindQuotationDetailForPreEmision(requestBody.getQuotationNumber()))
				.thenReturn(quotationInfo);

		//plan 01
		requestBody.getProduct().getPlan().setId("01");

		//cuenta de cliente
		requestBody.getRelatedContracts().get(0).getContractDetails().getProductType().setId("ACCOUNT");
		requestBody.getRelatedContracts().get(0).getContractDetails().setNumber("00110130290299972582");

		requestBody.getFirstInstallment().setIsPaymentRequired(true);
		PolicyInspectionDTO inspection = new PolicyInspectionDTO();
		inspection.setIsRequired(true);
		inspection.setFullName("Jose Artica");
		inspection.setContactDetails(requestBody.getHolder().getContactDetails());
		requestBody.setInspection(inspection);

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(1,validation.getParticipants().size());

		//Verifica que llame a las librerias externas y no al internal api connector
		verify(pisdR226, times(1)).executeFindQuotationIfExistInContract(any());
		verify(pisdr601, times(1)).executeFindQuotationDetailForPreEmision(any());
		verify(pisdR012, times(1)).executeGetRolesByProductAndModality(any(),any());
		verify(pisdR226, times(1)).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, times(1)).executePreFormalizationInsurance(any());
		verify(pisdR012, times(1)).executeMultipleInsertionOrUpdate(any(),any());
		verify(internalApiConnectorImpersonation, times(0)).exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any());
	}


	/*
	CASO 6: PRODUCTO VEHICULAR CON UNA TARJETA, CLIENTE PASAPORTE, PLAN 02, CON RESPONSABLE DE PAGO PASAPORTE
	 */
	@Test
	public void executeTestFlowVehicularCase6(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,2));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		//CLIENTE PASAPORTE
		requestBody.getHolder().getIdentityDocument().getDocumentType().setId("PASSPORT");
		requestBody.getHolder().getIdentityDocument().setNumber("1000867709");
		requestBody.getParticipants().get(0).getIdentityDocument().getDocumentType().setId("PASSPORT");
		requestBody.getParticipants().get(0).getIdentityDocument().setNumber("1000867709");

		//producto vehicular
		requestBody.getProduct().setId("830");

		quotationInfo.put("INSURANCE_BUSINESS_NAME","VEHICULAR");
		quotationInfo.put("INSURANCE_PRODUCT_ID",1);
		quotationInfo.put("INSURANCE_PRODUCT_DESC","Seguro VEHICULAR");
		quotationInfo.put("CONTRACT_DURATION_TYPE","A");
		quotationInfo.put("CONTRACT_DURATION_NUMBER",1);
		quotationInfo.put("INSURANCE_MODALITY_NAME","PLAN FULL");
		quotationInfo.put("INSURANCE_COMPANY_QUOTA_ID","ba3c7d41-65ce-4582-bde2-08f21311fbc9");
		when(pisdr601.executeFindQuotationDetailForPreEmision(requestBody.getQuotationNumber()))
				.thenReturn(quotationInfo);

		//plan 02
		requestBody.getProduct().getPlan().setId("02");

		//tarjeta de cliente
		requestBody.getRelatedContracts().get(0).getContractDetails().getProductType().setId("CARD");
		requestBody.getRelatedContracts().get(0).getContractDetails().setNumber("4919108221879326");

		requestBody.getFirstInstallment().setIsPaymentRequired(true);
		PolicyInspectionDTO inspection = new PolicyInspectionDTO();
		inspection.setIsRequired(true);
		inspection.setFullName("Jose Artica");
		inspection.setContactDetails(requestBody.getHolder().getContactDetails());
		requestBody.setInspection(inspection);

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(1,validation.getParticipants().size());

		//Verifica que llame a las librerias externas y no al internal api connector
		verify(pisdR226, times(1)).executeFindQuotationIfExistInContract(any());
		verify(pisdr601, times(1)).executeFindQuotationDetailForPreEmision(any());
		verify(pisdR012, times(1)).executeGetRolesByProductAndModality(any(),any());
		verify(pisdR226, times(1)).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, times(1)).executePreFormalizationInsurance(any());
		verify(pisdR012, times(1)).executeMultipleInsertionOrUpdate(any(),any());
		verify(internalApiConnectorImpersonation, times(0)).exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any());
	}


	/*
	CASO 7: OTRO PRODUCTO QUE TIENE PARTICIPANTES BENEFICIARIOS
	 */
	@Test
	public void executeTestCase7() throws IOException {
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
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(3,validation.getParticipants().size());
	}


	/*
	CASO 8: OTRO PRODUCTO QUE TIENE PARTICIPANTES ASEGURADOS
	 */

	@Test
	public void executeTestCase8(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,2));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		icr3Response.getIcmrys3().setOFICON("7794");
		when(rbvdR602.executePreFormalizationInsurance(Mockito.anyObject())).thenReturn(icr3Response);

		//Se agrega asegurado
		ParticipantDTO insured = mockCreateParticipant("FOREIGNERS","97793201","69503241210","INSURED");
		requestBody.getParticipants().add(insured);

		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,1,1});

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(2,validation.getParticipants().size());
	}


	/*
	CASO 9: OTRO PRODUCTO QUE TIENE PARTICIPANTE ENDOSATARIO
	 */

	@Test
	public void executeTestCase9(){
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
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
		assertEquals(2,validation.getParticipants().size());
	}


	//CASOS DE ERROR


	/*
	CASO 10: CASO DE ERROR AL LLAMAR AL EVENTO
	 */

	@Test
	public void executeTestErrorCallEventCase10(){
		when(applicationConfigurationService.getDefaultProperty("flag.callevent.createinsured.for.preemision","N")).thenReturn("S");
		when(this.internalApiConnectorImpersonation.exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Integer>)any())).
				thenThrow(new RestClientException("CONNECTION ERROR"));

		requestBody.getProduct().setId("842");
		requestBody.setHeaderOperationDate("2024-05-21");
		requestBody.setHeaderOperationTime("14:35:36");

		PolicyDTO validation = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(validation);
		assertNotNull(validation.getProduct());
		assertNotNull(validation.getParticipants());
	}


	/*
	CASO 11: CASO DE ERROR CUANDO LA COTIZACIÓN YA EXISTE EN LA TABLA DE CONTRATO
	 */

	@Test
	public void executeTestQuotationExistInContractCase11(){

		when(pisdR226.executeFindQuotationIfExistInContract(requestBody.getQuotationNumber())).thenReturn(true);
		requestBody.getProduct().setId("842");

		PolicyDTO response = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNull(response);
		assertEquals(1,this.context.getAdviceList().size());
		assertEquals(RBVDMessageError.QUOTATION_EXIST_IN_CONTRACT.getAdviceCode(),this.context.getAdviceList().get(0).getCode());

		// Verificar que los métodos posteriores no se ejecutan
		verify(pisdR226, never()).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, never()).executePreFormalizationInsurance(any());
	}


	/*
	CASO 12: CASO DE ERROR CUANDO LA COTIZACIÓN NO EXISTE EN LA TABLA DE COTIZACIONES
	 */

	@Test
	public void executeTestQuotationNotExistCase12(){
		when(pisdr601.executeFindQuotationDetailForPreEmision(requestBody.getQuotationNumber()))
				.thenReturn(Collections.emptyMap());

		PolicyDTO response = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNull(response);
		assertEquals(1,this.context.getAdviceList().size());
		assertEquals(RBVDMessageError.QUOTATION_NOT_EXIST.getAdviceCode(),this.context.getAdviceList().get(0).getCode());

		// Verificar que los métodos posteriores no se ejecutan
		verify(pisdR226, never()).executeFindPaymentPeriodByType(any());
		verify(rbvdR602, never()).executePreFormalizationInsurance(any());
	}


	/*
	CASO 13: CASO DE ERROR CUANDO SE LLAMA A LA ICR3 Y ESTE DEVUELVE UN ERROR DE HOST ADVICE
	 */

	@Test
	public void executeTestResponseWithHostAdviceErrorCase13(){
		icr3Response.setHostAdviceCode(Collections.singletonList(new HostAdvice("ICER024", "FECHA DE INICIO DE COBERT VACIO")));
		when(rbvdR602.executePreFormalizationInsurance(Mockito.anyObject())).thenReturn(icr3Response);

		PolicyDTO response = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNull(response);
		assertEquals(1,this.context.getAdviceList().size());
		assertEquals("ICER024",this.context.getAdviceList().get(0).getCode());

		// Verificar que no inserte contrato ni participantes
		verify(pisdR226, never()).executeInsertInsuranceContract(any());
		verify(pisdR012, never()).executeMultipleInsertionOrUpdate(any(),any());
	}


	/*
	CASO 14: CASO DE ERROR AL INSERTAR EN LA TABLA DE CONTRATO
	 */

	@Test
	public void executeTestErrorInsertContractCase14(){
		when(pisdR226.executeInsertInsuranceContract(Mockito.anyMap())).thenReturn(0);
		requestBody.getProduct().setId("842");

		PolicyDTO response = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNull(response);
		assertEquals(1,this.context.getAdviceList().size());
		assertEquals(RBVDMessageError.ERROR_INSERT_INSURANCE_CONTRACT.getAdviceCode(),this.context.getAdviceList().get(0).getCode());

		// Verificar que no inserte participantes
		verify(pisdR012, never()).executeMultipleInsertionOrUpdate(any(),any());
	}


	/*
	CASO 15: CASO DE ERROR AL INSERTAR EN LA TABLA DE PARTICIPANTES
	 */
	@Test
	public void executeTestErrorInsertParticipantsCase15(){
		List<Map<String, Object>> rolesFromDB = mockGetRolesFromDB(Arrays.asList(1,3,7));
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), rolesFromDB);

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);
		when(pisdR012.executeMultipleInsertionOrUpdate(Mockito.anyString(),Mockito.any()))
				.thenReturn(new int[]{1,0,1});

		PolicyDTO response = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNull(response);
		assertEquals(1,this.context.getAdviceList().size());
		assertEquals(RBVDMessageError.ERROR_INSERT_PARTICIPANTS.getAdviceCode(),this.context.getAdviceList().get(0).getCode());
	}


	/*
	CASO 16: CASO DONDE NO SE REGISTRARON ROLES POR PRODUCTO Y NO REGISTRA EN LA TABLA DE PARTICIPANTES
	 */
	@Test
	public void executeTestRolesByProductIsEmptyCase16(){
		Map<String, Object> result = new HashMap<>();
		result.put(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue(), Collections.emptyList());

		when(pisdR012.executeGetRolesByProductAndModality(Mockito.any(),Mockito.anyString()))
				.thenReturn(result);

		PolicyDTO response = rbvdr415.executeLogicPreFormalization(requestBody);

		assertNotNull(response);

		// Verificar que no inserte participantes
		verify(pisdR012, never()).executeMultipleInsertionOrUpdate(any(),any());
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
