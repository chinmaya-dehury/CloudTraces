import axios from 'axios';
import { getStorageApiUrl } from '@/config';

const storageApiUrl = getStorageApiUrl();

export async function uploadTraceFile(form) {
    const formData = new FormData();
    let result;

    const { provider, traceType, delimiter, file } = form;

    formData.append('provider', provider);
    formData.append('traceType', traceType);
    formData.append('delimiter', delimiter);
    formData.append('file', file);

    console.log(storageApiUrl);

    await axios.post(`${storageApiUrl}/files`, formData)
        .then(res => {
            console.log(res);
            result = res.data;
        })
        .catch(e => {
            console.log(e);
            console.log(e.response);
            result = e.response.data;
        });

    return result;
}

export async function getAllTraceFiles() {
    let response;

    await axios.get(`${storageApiUrl}/files`)
        .then(res => {
            response = res?.data;
        })
        .catch(err => {
            response = err?.response?.data;
        });

    return response ?? [];
}

export async function downloadTraceFile(fileName) {
    axios.get(`${storageApiUrl}/files/${fileName}`,{ responseType: 'blob' })
        .then(res => {
           const url = window.URL.createObjectURL(new Blob([res.data]));
           const link = document.createElement('a');
           link.href = url;
           link.setAttribute('download', fileName);
           document.body.appendChild(link);
           link.click();
        });
}
