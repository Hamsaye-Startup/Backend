package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.geos.models.AddressDetailsEntity;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.geos.requests.AddressRequests;
import com.microservices.warehouse.geos.responses.AddressResponse;
import com.microservices.warehouse.geos.services.AddressService;
import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.*;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import com.microservices.warehouse.storages.models.keys.FavouritesId;
import com.microservices.warehouse.storages.requests.StorageRequest;
import com.microservices.warehouse.storages.responses.StorageFlagsResponse;
import com.microservices.warehouse.storages.responses.StorageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StorageServiceManagementTest {

    @InjectMocks
    private StorageServiceManagement underTest;

    @Mock
    private StorageMapper storageMapper;

    @Mock
    private StorageService storageService;

    @Mock
    private AddressService addressService;

    @Mock
    private BookmarkService bookmarkService;

    @Mock
    private FavouritesBookService favouritesBookService;

    @Mock
    private ReservationService reservationService;

    private final UUID userId = UUID.randomUUID();
    private final Long storageId = 1001L;
    private final StorageRequest storageRequest = StorageRequest.builder()
            .category("BUSINESS")
            .amount(200D)
            .discountAmount(0D)
            .width(12)
            .height(13)
            .desc("This is a simple description for testing storage request")
            .build();
    private final StorageEntity storage = StorageEntity.builder()
            .owner(userId)
            .category(StorageCategoryEnum.valueOf(storageRequest.category()))
            .width(storageRequest.width())
            .height(storageRequest.height())
            .amount(storageRequest.amount())
            .discountAmount(storageRequest.discountAmount())
            .desc(storageRequest.desc())
            .enabled(true)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_insert_storage_successful() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();
        StorageResponse expectedResponse = createStorageResponse(persistedStorage);

        // mock
        Mockito.when(storageMapper.toStorage(storageRequest, userId))
                .thenReturn(storage);
        Mockito.when(storageService.persist(storage))
                .thenReturn(persistedStorage);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.insertStorage(storageRequest, userId);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void should_update_storage_address_successful() {
        // given
        // generate request entities
        AddressRequests addressRequests = createAddressRequests();
        AddressEntity address = createAddressEntity(addressRequests);
        StorageEntity persistedStorage = createPersistedStorage();
        StorageEntity updatedStorage = createUpdatedStorage(address);
        StorageResponse expectedResponse = createStorageResponse(persistedStorage, address);

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(persistedStorage);
        Mockito.when(addressService.generateAddress(addressRequests))
                        .thenReturn(address);
        Mockito.when(storageService.updateAddress(updatedStorage))
                        .thenReturn(updatedStorage);
        Mockito.when(storageMapper.toResponse(updatedStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.updateStorageAddress(storageId, addressRequests);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void should_update_storage_detail_successful() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();
        StorageEntity updatedStorage = createUpdatedStorage();
        StorageResponse expectedResponse = createStorageResponse(updatedStorage);

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(persistedStorage);
        Mockito.when(storageMapper.toStorage(storageRequest, storage.getOwner(), storage.getAddress(), storageId))
                .thenReturn(updatedStorage);
        Mockito.when(storageService.updateStorage(updatedStorage))
                .thenReturn(updatedStorage);
        Mockito.when(storageMapper.toResponse(updatedStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.updateStorageDetails(storageId, storageRequest);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void should_verify_storage_by_id_successful() {
        // given
        // generate request entities
        AddressRequests addressRequests = createAddressRequests();
        AddressEntity address = createAddressEntity(addressRequests);
        StorageEntity persistedStorage = createUpdatedStorage(address);

        StorageEntity verifiedStorage = createUpdatedStorage(address);
        verifiedStorage.setVerified(StorageVerifiedEnum.VERIFIED);

        StorageResponse expectedResponse = createStorageResponse(verifiedStorage, address);

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(persistedStorage);
        Mockito.when(storageService.updateStorage(verifiedStorage))
                .thenReturn(verifiedStorage);
        Mockito.when(storageMapper.toResponse(verifiedStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.verifyStorageById(storageId);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void should_remove_legal_storage_by_id_successful() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(persistedStorage);

        // then & when
        // assertion
        assertDoesNotThrow(() -> underTest.removeStorageById(storageId, StorageStatusEnum.ON_BLOCK_STASH));
    }

    @Test
    void should_change_storage_displayable_flag_successful() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();

        StorageEntity disabledStorage = createPersistedStorage();
        disabledStorage.setEnabled(false);

        StorageResponse expectedResponse = createStorageResponse(disabledStorage);

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(persistedStorage);
        Mockito.when(storageService.updateStorage(disabledStorage))
                .thenReturn(disabledStorage);
        Mockito.when(storageMapper.toResponse(disabledStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.displayStorage(storageId, false);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void check_finding_storage_by_id_successful_without_markers() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(false);
        persistedStorage.setFavourite(false);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);

        // mock
        Mockito.when(storageService.findStorageById(storageId, true, StorageStatusEnum.ON_BLOCK_STASH))
                .thenReturn(persistedStorage);
        Mockito.when(bookmarkService.findByIdAndStorageId(userId, storageId))
                .thenReturn(null);
        Mockito.when(favouritesBookService.findByIdAndStorageId(userId, storageId))
                .thenReturn(null);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.findStorageById(storageId, userId);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void check_finding_storage_by_id_successful_with_markers() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(true);
        persistedStorage.setFavourite(true);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);

        // mock
        Mockito.when(storageService.findStorageById(storageId, true, StorageStatusEnum.ON_BLOCK_STASH))
                .thenReturn(persistedStorage);
        Mockito.when(bookmarkService.findByIdAndStorageId(userId, storageId))
                .thenReturn(new BookmarkEntity(new BookmarkId(userId, persistedStorage), LocalDateTime.now()));
        Mockito.when(favouritesBookService.findByIdAndStorageId(userId, storageId))
                .thenReturn(new FavouritesBookEntity(new FavouritesId(userId, persistedStorage), LocalDateTime.now()));
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        StorageResponse result = underTest.findStorageById(storageId, userId);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void check_finding_storage_page_by_user_id_successful() {
        // given
        // generate request entities
        StorageEntity persistedStorage = createPersistedStorage();
        PageImpl<StorageEntity> persistedStorages = new PageImpl<>(Collections.singletonList(persistedStorage));
        List<BookmarkEntity> bookmarks = Collections.singletonList(new BookmarkEntity(new BookmarkId(userId, persistedStorage), LocalDateTime.now()));
        List<FavouritesBookEntity> favourites = Collections.singletonList(new FavouritesBookEntity(new FavouritesId(userId, persistedStorage), LocalDateTime.now()));

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(true);
        persistedStorage.setFavourite(true);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);
        PageImpl<StorageResponse> expectedResponses = new PageImpl<>(Collections.singletonList(expectedResponse), Pageable.unpaged(), 1);

        // mock
        Mockito.when(storageService.findStoragesByOwner(userId, Pageable.unpaged()))
                .thenReturn(persistedStorages);
        Mockito.when(bookmarkService.findByUserId(userId))
                .thenReturn(bookmarks);
        Mockito.when(favouritesBookService.findByUserId(userId))
                .thenReturn(favourites);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        Page<StorageResponse> results = underTest.findStoragesByUserId(userId, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1L, results.getContent().size());
        assertEquals(expectedResponses.getContent(), results.getContent());
    }

    @Test
    void check_finding_storage_page_successful_with_marker() {
        // given
        // declare the inputs
        String category = "BUSINESS";
        LocalDate fromDate = LocalDateTime.now().minusDays(1L).toLocalDate(), toDate = LocalDateTime.now().plusDays(1L).toLocalDate();

        // generate the entities
        StorageCategoryEnum categoryEnum = StorageCategoryEnum.valueOf(category);
        StorageEntity persistedStorage = createPersistedStorage();
        Page<StorageEntity> storages = new PageImpl<>(Collections.singletonList(persistedStorage));
        List<BookmarkEntity> bookmarks = Collections.singletonList(new BookmarkEntity(new BookmarkId(userId, persistedStorage), LocalDateTime.now()));
        List<FavouritesBookEntity> favourites = Collections.singletonList(new FavouritesBookEntity(new FavouritesId(userId, persistedStorage), LocalDateTime.now()));

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(true);
        persistedStorage.setFavourite(true);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);
        PageImpl<StorageResponse> expectedResponses = new PageImpl<>(Collections.singletonList(expectedResponse), Pageable.unpaged(), 1);

        // mock
        Mockito.when(storageMapper.convertStorageCategory(category))
                .thenReturn(categoryEnum);
        Mockito.when(storageService.findStoragesByCategory(categoryEnum, true, StorageStatusEnum.ON_BLOCK_STASH, Pageable.unpaged()))
                .thenReturn(storages);
        Mockito.when(bookmarkService.findByUserId(userId))
                .thenReturn(bookmarks);
        Mockito.when(favouritesBookService.findByUserId(userId))
                .thenReturn(favourites);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        Page<StorageResponse> results = underTest.findStorages(category, fromDate, toDate, Pageable.unpaged(), userId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(expectedResponses.getContent(), results.getContent());
    }

    @Test
    void check_finding_storage_page_successful_without_marker() {
        // given
        // declare the inputs
        String category = "BUSINESS";
        LocalDate fromDate = LocalDateTime.now().minusDays(1L).toLocalDate(), toDate = LocalDateTime.now().plusDays(1L).toLocalDate();

        // generate the entities
        StorageCategoryEnum categoryEnum = StorageCategoryEnum.valueOf(category);
        StorageEntity persistedStorage = createPersistedStorage();
        Page<StorageEntity> storages = new PageImpl<>(Collections.singletonList(persistedStorage));

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(false);
        persistedStorage.setFavourite(false);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);
        PageImpl<StorageResponse> expectedResponses = new PageImpl<>(Collections.singletonList(expectedResponse), Pageable.unpaged(), 1);

        // mock
        Mockito.when(storageMapper.convertStorageCategory(category))
                .thenReturn(categoryEnum);
        Mockito.when(storageService.findStoragesByCategory(categoryEnum, true, StorageStatusEnum.ON_BLOCK_STASH, Pageable.unpaged()))
                .thenReturn(storages);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        Page<StorageResponse> results = underTest.findStorages(category, fromDate, toDate, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(expectedResponses.getContent(), results.getContent());
    }

    @Test
    void check_searching_storage_page_by_postal_code_successful_with_marker() {
        // given
        // declare the inputs
        String value = "4311";

        // generate the entities
        AddressRequests addressRequests = createAddressRequests();
        AddressEntity address = createAddressEntity(addressRequests);
        StorageEntity persistedStorage = createUpdatedStorage(address);
        Page<StorageEntity> storages = new PageImpl<>(Collections.singletonList(persistedStorage));
        List<BookmarkEntity> bookmarks = Collections.singletonList(new BookmarkEntity(new BookmarkId(userId, persistedStorage), LocalDateTime.now()));
        List<FavouritesBookEntity> favourites = Collections.singletonList(new FavouritesBookEntity(new FavouritesId(userId, persistedStorage), LocalDateTime.now()));

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(true);
        persistedStorage.setFavourite(true);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);
        PageImpl<StorageResponse> expectedResponses = new PageImpl<>(Collections.singletonList(expectedResponse), Pageable.unpaged(), 1);

        // mock
        Mockito.when(storageService.searchStoragesByValue(value, true, Pageable.unpaged()))
                .thenReturn(storages);
        Mockito.when(bookmarkService.findByUserId(userId))
                .thenReturn(bookmarks);
        Mockito.when(favouritesBookService.findByUserId(userId))
                .thenReturn(favourites);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        Page<StorageResponse> results = underTest.searchStorages(value, Pageable.unpaged(), userId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(expectedResponses.getContent(), results.getContent());
    }

    @Test
    void check_searching_storage_page_by_address_successful_without_marker() {
        // given
        // declare the inputs
        String value = "address";

        // generate the entities
        AddressRequests addressRequests = createAddressRequests();
        AddressEntity address = createAddressEntity(addressRequests);
        StorageEntity persistedStorage = createUpdatedStorage(address);
        Page<StorageEntity> storages = new PageImpl<>(Collections.singletonList(persistedStorage));

        // Marking the same storage entity as favourite and bookmarked
        persistedStorage.setMarked(false);
        persistedStorage.setFavourite(false);

        StorageResponse expectedResponse = createStorageResponse(persistedStorage);
        PageImpl<StorageResponse> expectedResponses = new PageImpl<>(Collections.singletonList(expectedResponse), Pageable.unpaged(), 1);

        // mock
        Mockito.when(storageService.searchStoragesByValue(value, true, Pageable.unpaged()))
                .thenReturn(storages);
        Mockito.when(storageMapper.toResponse(persistedStorage))
                .thenReturn(expectedResponse);

        // when
        Page<StorageResponse> results = underTest.searchStorages(value, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(expectedResponses.getContent(), results.getContent());
    }

    private AddressRequests createAddressRequests() {
        return AddressRequests.builder()
                .address("This is sample of address for testing storage")
                .addressCompat("This is sample of address")
                .lat("12")
                .lon("31")
                .postalCode("4311")
                .build();
    }

    private AddressEntity createAddressEntity(AddressRequests addressRequests) {
        return AddressEntity.builder()
                .address(addressRequests.address())
                .addressCompat(addressRequests.addressCompat())
                .coordinate(addressRequests.lat() + ";" + addressRequests.lon())
                .details(AddressDetailsEntity.builder().postalCode(addressRequests.postalCode()).build())
                .build();
    }

    private StorageEntity createPersistedStorage() {
        return StorageEntity.builder()
                .id(storageId)
                .owner(userId)
                .category(StorageCategoryEnum.BUSINESS)
                .width(storageRequest.width())
                .height(storageRequest.height())
                .amount(storageRequest.amount())
                .discountAmount(storageRequest.discountAmount())
                .desc(storageRequest.desc())
                .createdAt(LocalDateTime.now())
                .enabled(true)
                .build();
    }

    private StorageEntity createUpdatedStorage() {
        return StorageEntity.builder()
                .id(storageId)
                .owner(userId)
                .category(StorageCategoryEnum.BUSINESS)
                .width(storageRequest.width())
                .height(storageRequest.height())
                .amount(storageRequest.amount())
                .discountAmount(storageRequest.discountAmount())
                .desc(storageRequest.desc())
                .createdAt(LocalDateTime.now())
                .enabled(true)
                .build();
    }

    private StorageEntity createUpdatedStorage(AddressEntity address) {
        return StorageEntity.builder()
                .id(storageId)
                .owner(userId)
                .category(StorageCategoryEnum.BUSINESS)
                .width(storageRequest.width())
                .height(storageRequest.height())
                .amount(storageRequest.amount())
                .discountAmount(storageRequest.discountAmount())
                .desc(storageRequest.desc())
                .createdAt(LocalDateTime.now())
                .address(address)
                .enabled(true)
                .build();
    }

    private StorageResponse createStorageResponse(StorageEntity updatedStorage) {
        return StorageResponse.builder()
                .id(updatedStorage.getId())
                .owner(updatedStorage.getOwner())
                .category(updatedStorage.getCategory().getName())
                .width(updatedStorage.getWidth())
                .height(updatedStorage.getHeight())
                .amount(updatedStorage.getAmount())
                .discountAmount(updatedStorage.getDiscountAmount())
                .flags(StorageFlagsResponse.builder()
                        .verified(updatedStorage.getVerified())
                        .status(updatedStorage.getStatus())
                        .displayable(updatedStorage.isEnabled())
                        .marked(updatedStorage.isMarked())
                        .favourite(updatedStorage.isFavourite())
                        .build())
                .score(updatedStorage.getScore())
                .flags(StorageFlagsResponse.builder()
                        .verified(updatedStorage.getVerified())
                        .status(updatedStorage.getStatus())
                        .displayable(updatedStorage.isEnabled())
                        .marked(updatedStorage.isMarked())
                        .favourite(updatedStorage.isFavourite())
                        .build())
                .build();
    }

    private StorageResponse createStorageResponse(StorageEntity persistedStorage, AddressEntity address) {
        String[] coordinate = address.getCoordinate().split(";");
        return StorageResponse.builder()
                .id(persistedStorage.getId())
                .owner(persistedStorage.getOwner())
                .category(persistedStorage.getCategory().getName())
                .width(persistedStorage.getWidth())
                .height(persistedStorage.getHeight())
                .amount(persistedStorage.getAmount())
                .discountAmount(persistedStorage.getDiscountAmount())
                .address(AddressResponse.builder()
                        .address(address.getAddress())
                        .addressCompat(address.getAddressCompat())
                        .lon(coordinate[1])
                        .lat(coordinate[0])
                        .build())
                .flags(StorageFlagsResponse.builder()
                        .verified(persistedStorage.getVerified())
                        .status(persistedStorage.getStatus())
                        .displayable(persistedStorage.isEnabled())
                        .marked(persistedStorage.isMarked())
                        .favourite(persistedStorage.isFavourite())
                        .build())
                .score(persistedStorage.getScore())
                .build();
    }
}