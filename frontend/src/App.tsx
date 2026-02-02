import { useEffect, useState } from "react";
import CurrencyDropdown from "./components/CurrencyDropdown.tsx";
import CurrencyList from "./components/CurrencyList.tsx";
import type { Currencies, Currency } from "./types.tsx";

function App() {
  const [darkMode, setDarkMode] = useState(true);

  const [fromCurrency, setFromCurrency] = useState<Currency>({
    isoCode: "EUR",
    name: "Euro",
    rate: 1,
    updatedAt: "",
  });
  const [toCurrency, setToCurrency] = useState<Currency>({
    isoCode: "EUR",
    name: "Euro",
    rate: 1,
    updatedAt: "",
  });

  const [amount, setAmount] = useState<number>(0);
  const [error, setError] = useState<string>("");
  const [currencies, setCurrencies] = useState<Currencies[]>([]);

  useEffect(() => {
    async function fetchCurrencies() {
      const apiBase = import.meta.env.VITE_API_BASE_URL;
      const res = await fetch(apiBase + "/api/rates");
      const data = await res.json();
      setCurrencies(data);
    }
    fetchCurrencies();
  }, []);

  const resetError = () => error && setError("");

  const calculateAmount = (amount: number, to: number, from: number) =>
    (amount * (to / from)).toFixed(3);

  function swapCurrencies() {
    setFromCurrency(toCurrency);
    setToCurrency(fromCurrency);
  }

  function toggleDarkMode() {
    setDarkMode(!darkMode);
  }

  return (
    <div
      className={`min-h-screen transition-colors duration-300
            ${darkMode ? "bg-[#1E1B2E]" : "bg-[#EDE9FE]"}`}
    >
      <header
        className={`${darkMode ? "bg-[#2A2544]" : "bg-[#DDD6FE]"} px-6 py-3 shadow`}
      >
        <div
          className={`flex justify-between items-center
                 max-w-150 mx-auto`}
        >
          <h1
            className={`text-3xl sm:text-4xl font-bold
                    ${darkMode ? "text-[#DDD6FE]" : "text-[#4C1D95]"}`}
          >
            Ka-Ching
          </h1>

          <button
            onClick={toggleDarkMode}
            className={`relative inline-flex h-8 w-16 items-center rounded-full transition-colors duration-300
    ${darkMode ? "bg-[#7C3AED]" : "bg-[#E5E7EB]"}`}
          >
            <span
              className={`inline-flex h-6 w-6 transform items-center justify-center rounded-full bg-white shadow-md transition-all duration-300
      ${darkMode ? "translate-x-9" : "translate-x-1"}`}
            >
              <span className="text-xs">{darkMode ? "🌚" : "🌞"}</span>
            </span>
          </button>
        </div>
      </header>

      {/*Converter*/}
      <div className="flex justify-center">
        <form
          className={`w-full sm:w-[90%] md:w-[60%] lg:w-[35%] mt-8 pt-4 pb-8 px-8 rounded-3xl shadow-lg
                    ${darkMode ? "bg-[#2A2544]" : "bg-[#F5F3FF]"}`}
        >
          <h2
            className={`sm:text-2xl text-center mb-10
                        ${darkMode ? "text-[#DDD6FE]" : "text-[#5B21B6]"}`}
          >
            Currency Converter
          </h2>

          {/*Base Amount*/}
          <label
            className={`mb-1 block text-sm ${darkMode ? "text-[#C4B5FD]" : "text-[#4C1D95]"}`}
          >
            Amount
          </label>

          <div className="grid grid-cols-[1fr_auto] gap-2 items-center">
            <input
              type="text"
              className={`rounded-md w-full pl-2 py-2 outline-none
                            ${
                              darkMode
                                ? "bg-[#1E1B2E] text-white focus:ring-[#7C3AED]"
                                : "bg-white focus:ring-[#C4B5FD]"
                            }`}
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

            <CurrencyDropdown
              value={fromCurrency.isoCode}
              currencies={currencies}
              onChange={setFromCurrency}
              compact
              darkMode={darkMode}
            />
          </div>

          {/*Swap Button*/}
          <div className="flex justify-center my-6">
            <button
              type="button"
              onClick={swapCurrencies}
              className="h-12 w-12 bg-[#7C3AED] hover:bg-[#6D28D9] text-white rounded-full text-xl active:scale-95 transition"
            >
              ⇄
            </button>
          </div>

          {/*Converted Amount*/}
          <label
            className={`mb-1 block text-sm ${darkMode ? "text-[#C4B5FD]" : "text-[#4C1D95]"}`}
          >
            Converted Amount
          </label>

          <div className="grid grid-cols-[1fr_auto] gap-2 items-center">
            <input
              type="text"
              readOnly
              className={`rounded-md w-full pl-2 py-2 outline-none
                            ${darkMode ? "bg-[#1E1B2E] text-white" : "bg-white"}`}
              value={
                error === ""
                  ? calculateAmount(amount, toCurrency.rate, fromCurrency.rate)
                  : error
              }
            />

            <CurrencyDropdown
              value={toCurrency.isoCode}
              currencies={currencies}
              onChange={setToCurrency}
              compact
              darkMode={darkMode}
            />
          </div>

          {/*Result*/}
          {fromCurrency && toCurrency && (
            <div className="flex justify-center mt-6">
              <div
                className={`px-4 py-2 rounded-lg text-sm text-center
      ${
        darkMode ? "bg-[#1E1B2E] text-[#C4B5FD]" : "bg-[#FFFFFF] text-[#4C1D95]"
      }`}
              >
                Exchange rate: 1 {fromCurrency.isoCode} →{" "}
                {calculateAmount(1, toCurrency.rate, fromCurrency.rate)}{" "}
                {toCurrency.isoCode}
              </div>
            </div>
          )}
        </form>
      </div>

      {/*CurrencyList*/}
      <CurrencyList currencies={currencies} darkMode={darkMode} />
    </div>
  );
}

export default App;
