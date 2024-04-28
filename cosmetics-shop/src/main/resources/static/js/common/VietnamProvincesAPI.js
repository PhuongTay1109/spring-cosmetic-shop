const DISTRICT_API =  'https://api.viettelpost.vn/api/setting/listalldistrict'
const PROVINCE_API =  'https://api.viettelpost.vn/api/setting/listallprovince'
const WARD_API =  'https://api.viettelpost.vn/api/setting/listallwards'


export async function fetchProvince() { 
    const response = await fetch(PROVINCE_API);
    const provinces = await response.json();
    return provinces
}

// Pass provinceId to get list of districts in that province
export async function fetchDistrict(provinceId) {
    const response = await fetch(DISTRICT_API);
    let districts = await response.json();
    districts  = districts.filter(district => district.PROVINCE_ID == provinceId)
    console.log(districts)
    return districts
}

// Pass districtId to get list of districts in that province
export async function fetchWard(districtId) {
    const response = await fetch(WARD_API);
    let wards = await response.json();
    wards  = wards.filter(ward => ward.DISTRICT_ID == districtId)
    console.log(wards)
    return wards
}





