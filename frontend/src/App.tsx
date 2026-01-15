import { useState } from "react";
import CurrencyList from "./components/CurrencyList";

interface Props {
    isoCode: string;
    name: string;
    rate: number;
    updatedAt: string;
}

function App() {
    const [fromCurrency, setFromCurrency] = useState<Props>({isoCode: "EUR", name: "Euro", rate: 1, updatedAt: ""});
    const [toCurrency, setToCurrency] = useState<Props>({isoCode: "EUR", name: "EUR", rate: 1, updatedAt: ""});

    const [amount, setAmount] = useState<number>(0);

    const [error, setError] = useState<string>("");

    const resetError = () => {
        if (error !== "") {
            setError("");
        }
    };

    const calculateAmount = (amount:number, to:number, from:number) => {
        return (amount * (to / from)).toFixed(2);
    }

    function swapCurrencies() {
        setFromCurrency(toCurrency);
        setToCurrency(fromCurrency);
    }

    return (
        <div className="bg-[#2A2A2A] h-screen">
            <header className="flex justify-center items-center bg-gray-100">
                <h1 className="text-4xl font-bold m-2">Ka-Ching</h1>
            </header>

            <div className="flex justify-center items-center">
                <form className="w-[35%] mt-20 bg-[#cecece] p-4 rounded">
                    <h2 className="text-2xl font-bold underline mb-10">
                        Currency Converter
                    </h2>

                    <div className="grid grid-cols-[auto_1fr] items-baseline gap-x-3 gap-y-2">
                        <label>Base Amount</label>
                        <CurrencyList
                            value={fromCurrency?.isoCode}
                            onChange={setFromCurrency}
                        />
                        <input
                            type="text"
                            className="bg-white rounded-md text-l w-full pl-2 py-1 outline-none col-span-2"
                            placeholder="Enter amount..."
                            onChange={(e) => {
                                if (e.target.value.match(/^[0-9]+(\.[0-9]+)?$/)) {
                                    resetError();
                                    setAmount(parseFloat(e.target.value));
                                } else {
                                    setError("Enter a valid number...")
                                    setAmount(0);
                                }
                            }}
                        />
                    </div>

                    <div className="flex justify-center my-8">
                        <button
                            type="button"
                            onClick={swapCurrencies}
                            className="px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700"
                        >
                            ⇄ Swap
                        </button>
                    </div>

                    <div className="grid grid-cols-[auto_1fr] items-baseline gap-x-3 gap-y-2">
                        <label className="whitespace-nowrap">
                            Converted amount
                        </label>

                        <CurrencyList
                            value={toCurrency?.isoCode}
                            onChange={setToCurrency}
                        />

                        <input
                            type="text"
                            className="bg-white rounded-md text-l w-full pl-2 py-1 outline-none col-span-2"
                            placeholder="0.00"
                            readOnly
                            value={error === "" ? calculateAmount(amount, toCurrency.rate, fromCurrency.rate) : error}
                        />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default App;
