import { useEffect, useState } from "react";

export default function CurrencyList() {
    const [currencies, setCurrencies] = useState<string[]>([]);
    const [selectedCurrency, setSelectedCurrency] = useState("");

    useEffect(() => {
        async function fetchCurrencies() {
            try {
                const res = await fetch("http://localhost:8080/api/rates");
                const data = await res.json();
                setCurrencies(data);
            } catch (error) {
                console.log(error);
            }
        }

        fetchCurrencies();
    }, []);

    useEffect(() => {
        if (!selectedCurrency) return;

        async function fetchCurrency() {
            try {
                const res = await fetch(
                    "http://localhost:8080/api/rates/" + selectedCurrency
                );
                const data = await res.json();
                console.log(data);
            } catch (error) {
                console.log(error);
            }
        }

        fetchCurrency();
    }, [selectedCurrency]);

    return (
        <select
            value={selectedCurrency}
            onChange={(e) => setSelectedCurrency(e.target.value)}
            className=""
        >
            {currencies.map((c) => (
                <option key={c} value={c}>
                    {c}
                </option>
            ))}
        </select>
    );
}