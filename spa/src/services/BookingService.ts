import {HotelRestControllerApi, RoomCategoryDTO, ServiceDTO} from "../openapi/ts_openapi_client";
import {BookingData} from "../openapi/ts_openapi_client/model/BookingData";

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

    createBookingRest(bookingData: BookingData): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            const api = new HotelRestControllerApi();

            api.createBooking(bookingData).then(response => {
                resolve(response.body);
            }).catch(error => {
                    reject(error);
            });
        });
    }
}

export default new BookingService();