import { useState } from "react";
import CurrencyList from "./components/CurrencyList";

interface Props {
    isoCode: string;
    name: string;
    rate: number;
    updatedAt: string;
}

function App() {
    const [fromCurrency, setFromCurrency] = useState<Props>();
    const [toCurrency, setToCurrency] = useState<Props>();

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

                    <div className="mb-4">
                        <label>Base Amount</label>
                        <CurrencyList
                            value={fromCurrency?.isoCode}
                            onChange={setFromCurrency}
                        />
                        <input
                            type="text"
                            className="bg-white rounded-md m-2 text-xl pl-2 py-1"
                            placeholder="Enter amount..."
                        />
                    </div>

                    <div className="flex justify-center my-4">
                        <button
                            type="button"
                            onClick={swapCurrencies}
                            className="px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700"
                        >
                            ⇄ Swap
                        </button>
                    </div>

                    <div>
                        <label>Converted amount</label>
                        <CurrencyList
                            value={toCurrency?.isoCode}
                            onChange={setToCurrency}
                        />
                        <input
                            type="text"
                            className="bg-white rounded-md m-2 text-xl pl-2 py-1"
                            placeholder="0.00"
                        />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default App;
