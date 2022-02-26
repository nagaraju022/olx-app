package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.olx.delegate.OLXLoginDelegateService;
import com.olx.delegate.OLXMasterdataDelegateService;
import com.olx.dto.ADVStatus;
import com.olx.dto.Advertise;
import com.olx.dto.Category;
import com.olx.entity.AdvertiseEntity;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.repository.AdvertisesRepository;

@Service
public class AdvertisesServiceImpl implements AdvertisesService {

	@Autowired
	AdvertisesRepository advertisesRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	EntityManager entityManager;

	@Autowired
	OLXLoginDelegateService olxLoginServiceDelegate;

	@Autowired
	OLXMasterdataDelegateService olxMasterdataDelegateService;

	@Override
	public Advertise createAdvertises(Advertise advertises, String authToken) {
		boolean tokenValid = olxLoginServiceDelegate.isTokenValid(authToken);

		if (tokenValid == false) {
			throw new InvalidAuthTokenException(authToken);
		}

		List<Category> categories = olxMasterdataDelegateService.findAllMasterdataCategory();
		
		for (Category category : categories) {
			if (category.getId() == advertises.getCategory()) {
				advertises.setCategory(category.getId());
			}
		}
		
		List<ADVStatus> advStatusList = olxMasterdataDelegateService.findAllMasterdataStuatus();
		for (ADVStatus advStatus : advStatusList) {
			if (advStatus.getId()==advertises.getStatus()) {
				advertises.setStatus(advStatus.getId());
			}
		}

		AdvertiseEntity advertisesEntity = convertAdvertisesDtoToEntity(advertises);
		advertisesEntity = advertisesRepository.save(advertisesEntity);
		advertises = convertAdvertisesEntityToDto(advertisesEntity);
		return advertises;

	}

	@Override
	public Advertise updateAdvertisesById(int advertiseId, Advertise advertises, String authToken) {
		boolean tokenValid = olxLoginServiceDelegate.isTokenValid(authToken);
		if (tokenValid == false) {
			throw new InvalidAuthTokenException(authToken);
		}

		Optional<AdvertiseEntity> advertisesEntity = advertisesRepository.findById(advertiseId);
		if (advertisesEntity.isPresent()) {
			AdvertiseEntity advEntity = advertisesEntity.get();
			advertises.setId(advertiseId);
			advEntity = convertAdvertisesDtoToEntity(advertises);
			advEntity = advertisesRepository.save(advEntity);
			advertises = convertAdvertisesEntityToDto(advEntity);
			return advertises;
		}

		return null;
	}

	@Override
	public List<Advertise> getAllAdvertises(String authToken) {
		boolean tokenValid = olxLoginServiceDelegate.isTokenValid(authToken);

		if (tokenValid == false) {
			throw new InvalidAuthTokenException(authToken);
		}

		List<AdvertiseEntity> advList = advertisesRepository.findAll();
		List<Advertise> advertises = new ArrayList<Advertise>();
		for (AdvertiseEntity advertisesEntity : advList) {
			Advertise advertise = convertAdvertisesEntityToDto(advertisesEntity);
			advertises.add(advertise);
		}

		return advertises;
	}

	@Override
	public Advertise getAdvertisesById(int advertiseId, String authToken) {
		boolean tokenValid = olxLoginServiceDelegate.isTokenValid(authToken);

		if (tokenValid == false) {
			throw new InvalidAuthTokenException(authToken);
		}

		Optional<AdvertiseEntity> advertisesEntity = advertisesRepository.findById(advertiseId);
		if (advertisesEntity.isPresent()) {
			AdvertiseEntity advEntity = advertisesEntity.get();
			Advertise advertises = convertAdvertisesEntityToDto(advEntity);
			return advertises;
		}

		return null;
	}

	@Override
	public boolean deleteAdvertisesById(int advertiseId, String authToken) {
		boolean tokenValid = olxLoginServiceDelegate.isTokenValid(authToken);

		if (tokenValid == false) {
			throw new InvalidAuthTokenException(authToken);
		}
		advertisesRepository.deleteById(advertiseId);
		return true;
	}

	@Override
	public List<Advertise> getAdvertisesBySearchText(String searchText) {

		List<AdvertiseEntity> titleList = advertisesRepository.findByTitleContaining(searchText);
		List<Advertise> advertises = new ArrayList<Advertise>();
		for (AdvertiseEntity advertisesEntity : titleList) {
			Advertise advertise = convertAdvertisesEntityToDto(advertisesEntity);
			advertises.add(advertise);
		}

		return advertises;
	}

	private AdvertiseEntity convertAdvertisesDtoToEntity(Advertise advertises) {
		AdvertiseEntity advertisesEntity = this.modelMapper.map(advertises, AdvertiseEntity.class);
		return advertisesEntity;
	}

	private Advertise convertAdvertisesEntityToDto(AdvertiseEntity advertisesEntity) {
		Advertise advertises = this.modelMapper.map(advertisesEntity, Advertise.class);
		return advertises;
	}

	@Override
	public Advertise getAdvertiseById(int id) {
		Optional<AdvertiseEntity> advertisesEntity = advertisesRepository.findById(id);
		if (advertisesEntity.isPresent()) {
			AdvertiseEntity advEntity = advertisesEntity.get();
			Advertise advertises = convertAdvertisesEntityToDto(advEntity);
			return advertises;
		}

		return null;
	}

	@Override
	public List<Advertise> searchAdvertisesByFilterCriteria(String searchText, int categoryId, String postedBy,
			String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortedBy,
			int startIndex, int records) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertiseEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
		Root<AdvertiseEntity> rootEntity = criteriaQuery.from(AdvertiseEntity.class);

		// Predicate predicateSortedBy = null;
		
		Predicate predicateSearchText = criteriaBuilder.and();
		Predicate predicateDateCondition = criteriaBuilder.and();
		Predicate onDateCondition = criteriaBuilder.and();
		Predicate fromDateCondition = criteriaBuilder.and();
		Predicate fromLessDateCondition = criteriaBuilder.and();
		Predicate fromToDateCondition = criteriaBuilder.and();
		Predicate predicatePostedBy = criteriaBuilder.and();
		Predicate predicateCategoryId = criteriaBuilder.and();
		Predicate preDateCondition = criteriaBuilder.and();

		Predicate predicateFinal = criteriaBuilder.and();

		if(searchText!=null && !"".equals(searchText)) {
		Predicate predicateTitle = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
		Predicate predicateDescription = criteriaBuilder.like(rootEntity.get("description"), "%" + searchText + "%");
		predicateSearchText = criteriaBuilder.or(predicateTitle, predicateDescription);
		}

		if (postedBy != null && !"".equalsIgnoreCase(postedBy)) {
			predicatePostedBy = criteriaBuilder.equal(rootEntity.get("postedBy"), postedBy);
		}
		if (categoryId != 0) {
			predicateCategoryId = criteriaBuilder.equal(rootEntity.get("category"), categoryId);
		}

		if (dateCondition != null && !"".equals(dateCondition)) {

			if (dateCondition.equalsIgnoreCase("equals")) {
				onDateCondition = criteriaBuilder.equal(rootEntity.get("createdDate"), onDate);
			}
			if (dateCondition.equals("greaterthan")) {
				fromDateCondition = criteriaBuilder.greaterThan(rootEntity.get("createdDate"), fromDate);
			}

			if (dateCondition.equalsIgnoreCase("lessthan")) {
				 fromLessDateCondition =  criteriaBuilder.lessThan(rootEntity.get("createdDate"), fromDate);
			}

			if (dateCondition.equalsIgnoreCase("between")) {
				 fromToDateCondition = criteriaBuilder.between(rootEntity.get("createdDate"), fromDate, toDate);
			}
			
			preDateCondition =criteriaBuilder.or(onDateCondition,fromDateCondition,fromLessDateCondition,fromToDateCondition);
		}
		

		predicateFinal = criteriaBuilder.and(predicateSearchText,predicatePostedBy, preDateCondition, predicateCategoryId);
		criteriaQuery.where(predicateFinal);
		criteriaQuery.orderBy(criteriaBuilder.asc(rootEntity.get("title")));
		
		
		TypedQuery<AdvertiseEntity> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(startIndex);
		typedQuery.setMaxResults(records);
		List<AdvertiseEntity> resultEntityList = typedQuery.getResultList();

		List<Advertise> advertises = new ArrayList<>();
		for (AdvertiseEntity advertiseEntity : resultEntityList) {
			Advertise advertiseDto = convertAdvertisesEntityToDto(advertiseEntity);
			advertises.add(advertiseDto);
		}

		return advertises;
	}

}
