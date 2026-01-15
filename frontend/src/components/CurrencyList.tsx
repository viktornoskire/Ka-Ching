import {useEffect, useState} from "react";

interface Currency {isoCode: string
                name: string;
                rate: number;
                updatedAt: string;}

interface Props {
    value: string | undefined;
    onChange: (value: Currency) => void;
    compact?: boolean;
}

export default function CurrencyList({ value, onChange, compact }: Props) {
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
        const isoCode = e.target.value;
        const res = await fetch("http://localhost:8080/api/rates/" + isoCode);
        const data: Currency = await res.json();
        onChange(data);
    }

    return (
        <select
            value={value ?? ""}
            onChange={handleChange}
            className={`border rounded outline-none ${compact ? "px-3 py-2 bg-[#e5e5e5]" : "w-full p-2"}`}
        >
            {currencies.map(c => (
                <option key={c} value={c}>{c}</option>
            ))}
        </select>
    );
}

