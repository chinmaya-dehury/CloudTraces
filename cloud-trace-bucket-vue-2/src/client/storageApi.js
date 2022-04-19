import axios from "axios";
import { getStorageApiUrl } from "@/config";

const storageApiUrl = getStorageApiUrl();

export async function uploadTraceFile(form) {
    const formData = new FormData();
    let result;

    const {provider, traceType, delimiter, file} = form;

    formData.append('provider', provider);
    formData.append('traceType', traceType);
    formData.append('delimiter', delimiter);
    formData.append('file', file);

    await axios.post(`${storageApiUrl}/files`, formData)
        .then(res => {
            result = res.data;
        })
        .catch(e => {
            result = e.response.data;
        });

    return result;
}