import { useState } from "react";
import CurrencyList from "./components/CurrencyList";

interface Props {
    isoCode: string;
    name: string;
    rate: number;
    updatedAt: string;
}

function App() {
    const [fromCurrency, setFromCurrency] = useState<Props>({
        isoCode: "EUR",
        name: "Euro",
        rate: 1,
        updatedAt: ""
    });

    const [toCurrency, setToCurrency] = useState<Props>({
        isoCode: "EUR",
        name: "EUR",
        rate: 1,
        updatedAt: ""
    });

    const [amount, setAmount] = useState<number>(0);
    const [error, setError] = useState<string>("");

    const resetError = () => error && setError("");

    const calculateAmount = (amount: number, to: number, from: number) =>
        (amount * (to / from)).toFixed(4);

    function swapCurrencies() {
        setFromCurrency(toCurrency);
        setToCurrency(fromCurrency);
    }

    return (
        <div className="bg-[#2A2A2A] min-h-screen max-lg: px-3">
            <header className="flex justify-center items-center bg-gray-100 py-3">
                <h1 className="text-3xl sm:text-4xl font-bold">Ka-Ching</h1>
            </header>

            <div className="flex justify-center">
                <form className="w-full sm:w-[90%] md:w-[60%] lg:w-[35%] mt-8 bg-[#cecece] p-4 sm:p-6 rounded">
                    <h2 className="text-xl sm:text-2xl font-bold underline mb-6">
                        Currency Converter
                    </h2>

                    <label className="mb-1 block text-sm">Amount</label>

                    <div className="grid grid-cols-[1fr_auto] gap-2 items-center">
                        <input
                            type="text"
                            className="bg-white rounded-md w-full pl-2 py-2 outline-none"
                            placeholder="Enter amount..."
                            onChange={(e) => {
                                if (e.target.value.match(/^[0-9]+(\.[0-9]+)?$/)) {
                                    resetError();
                                    setAmount(parseFloat(e.target.value));
                                } else {
                                    setError("Enter a valid number...");
                                    setAmount(0);
                                }
                            }}
                        />

                        <CurrencyList
                            value={fromCurrency.isoCode}
                            onChange={setFromCurrency}
                            compact
                        />
                    </div>

                    <div className="flex justify-center my-6">
                        <button
                            type="button"
                            onClick={swapCurrencies}
                            className="h-12 w-12 bg-gray-800 text-white rounded-full text-xl active:scale-95">
                            ⇄
                        </button>
                    </div>

                    <label className="mt-6 mb-1 block text-sm">Converted Amount</label>

                    <div className="grid grid-cols-[1fr_auto] gap-2 items-center">
                        <input
                            type="text"
                            className="bg-white rounded-md w-full pl-2 py-2 outline-none"
                            readOnly
                            value={
                                error === ""
                                    ? calculateAmount(amount, toCurrency.rate, fromCurrency.rate)
                                    : error
                            }
                        />

                        <CurrencyList
                            value={toCurrency.isoCode}
                            onChange={setToCurrency}
                            compact
                        />
                    </div>
                    <div className="flex justify-center mt-6">
                        <input type="text" readOnly className={"bg-white rounded-md w-full pl-2 py-2 outline-none text-center"} value={
                            error === ""?
                                "1 " + fromCurrency.isoCode + " = " +
                                calculateAmount(1, toCurrency.rate, fromCurrency.rate) +
                                " " + toCurrency.isoCode   : "..."}/>
                    </div>
                </form>
            </div>
        </div>
    );
}


export default App;
