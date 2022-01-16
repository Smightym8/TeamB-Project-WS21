import {HotelRestControllerApi, RoomCategoryDTO, ServiceDTO} from "../openapi/ts_openapi_client";

class BookingService {
    fetchAllRoomCategoriesRest(): Promise<RoomCategoryDTO[]> {
        return new Promise<RoomCategoryDTO[]>((resolve, reject) => {
            const api = new HotelRestControllerApi();
            api.allRoomCategories().then(response => {
               resolve(response.body);
            });
        });
    }

    fetchAllServicesRest(): Promise<ServiceDTO[]> {
        return new Promise<ServiceDTO[]>((resolve, reject) => {
           const api = new HotelRestControllerApi();
           api.allServices().then(response => {
               resolve(response.body);
           });
        });
    }

    createBookingRest(bookingData: JSON): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            const api = new HotelRestControllerApi();

            api.createBooking(bookingData).then(response => {
                resolve(response.body);
            });
        });
    }
}

export default new BookingService();