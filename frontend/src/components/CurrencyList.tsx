import {useEffect, useState} from "react";

interface Currency {isoCode: string
                name: string;
                rate: number;
                updatedAt: string;}

interface Props {
    value: string | undefined;
    onChange: (value: Currency) => void;
}

export default function CurrencyList({ value, onChange }: Props) {
    const [currencies, setCurrencies] = useState<string[]>([]);

    useEffect(() => {
        async function fetchCurrencies() {
            const res = await fetch("http://localhost:8080/api/rates");
            const data = await res.json();
            setCurrencies(data);
        }

        fetchCurrencies();
    }, []);

    async function handleChange(e: React.ChangeEvent<HTMLSelectElement>) {
        const isoCode: string = e.target.value;

        try {
            const res= await fetch("http://localhost:8080/api/rates/" + isoCode);
            const data: Currency = await res.json();
            onChange(data);
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <select
            value={value ?? ""}
            onChange={handleChange}
            className="ml-auto mr-0 p-1 border rounded block outline-none"
        >
            <option value="" disabled>Select Currency</option>
            {currencies.map(c => (
                <option key={c} value={c}>
                    {c}
                </option>
            ))}
        </select>
    );
}
