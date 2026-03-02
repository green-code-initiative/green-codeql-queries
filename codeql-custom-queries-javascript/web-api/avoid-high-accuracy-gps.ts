navigator.geolocation.getCurrentPosition(success, error, {
    enableHighAccuracy: true,
    timeout: 5000
});

const powerHungryOptions = {
    enableHighAccuracy: true,
    maximumAge: 0
};
navigator.geolocation.getCurrentPosition(success, error, powerHungryOptions);

navigator.geolocation.watchPosition(success, error, {
    enableHighAccuracy: true
});


navigator.geolocation.getCurrentPosition(success, error, {
    enableHighAccuracy: false,
    timeout: 10000
});

navigator.geolocation.getCurrentPosition(success, error, {
    timeout: 5000
});

navigator.geolocation.getCurrentPosition(success);

function success(pos: any) { console.log(pos); }
function error(err: any) { console.warn(err); }