import {useEffect, useState} from "react";

interface Currency {
    isoCode: string;
    rate: number;
    name: string;
    recordedAt: Date;
}

export default function CurrencyList() {
    const [currencies, setCurrencies] = useState<Currency[]>([]);

    useEffect(() => {
        async function fetchCurrencies() {
            try {
                const res = await fetch("http://localhost:8080/currencies");
                const data = await res.json();
                setCurrencies(data);
            } catch (error) {
                console.log(error);
            }
        }
        fetchCurrencies().then(() => console.log("Fetched currencies"));
    })

    return (
        <>
            <select>
                {currencies.map(currency => <option>{currency.isoCode}</option>)}
            </select>
        </>
    )
}